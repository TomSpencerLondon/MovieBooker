#### Diary entries (Dmitri Bontoft + Tom Spencer)

### 29/3/23
- started amendBooking
- decided to do TDD to start out on amendBooking
- realised that service was difficult to test because it was attracting logic
- separated a Transaction class
- created AmendBookingTransaction and BookingTransaction
    - these return the payment for the booking
- Now the bookingService has:

```java
public class BookingService {
    public Payment calculatePayment(Booking booking) {
        MovieGoer movieGoer = movieGoerRepository.findById(booking.movieGoerId())
                .orElseThrow(IllegalArgumentException::new);

        BookingTransaction bookingTransaction = new BookingTransaction(booking, movieGoer, LocalDateTime.now());
        return bookingTransaction.payment();
    }
}

```
- this moves the logic out of the service into the Transaction
- the service is delegating as opposed to doing logic
- the Transaction now contains the Booking and the Payment

Questions:
- Can we reuse the BookingForm?
- Can we reuse the same Booking screen?

### 30/3/23
- we implemented amend booking
- realised booking form didn't work perfectly
- we had to pass in the bookingId and additionalSeats as separate parameters
```java

@Controller
public class MovieController {
  @PostMapping("/amendBooking")
  public String makeAmendBooking(@ModelAttribute("amendBookingForm") AmendBookingForm amendBookingForm,
                                 @RequestParam("bookingId") Long bookingId,
                                 @RequestParam("additionalSeats") int additionalSeats) {
    Payment payment = AmendBookingForm.toPayment(amendBookingForm);

    if (bookingService.canAmendBooking(bookingId, additionalSeats)) {
      bookingService.amendBooking(bookingId, additionalSeats, payment);

      return "redirect:/bookings";
    } else {
      return "redirect:/seatsNotAvailable?bookingId=" + bookingId;
    }
  }
}
```
- added seatsNotAvailable page for situation where there is concurrent users
- realised we needed to cover the same situation for a normal booking
- refactored booking form factory to remove duplication between amendments and booking

```java
public class BookingForm {
  public static BookingForm from(Booking booking, Payment payment) {
    BookingForm bookingForm = from(booking);
    bookingForm.setAmountToPay(payment.amountPaid());
    bookingForm.setUpdatedLoyaltyPoints(payment.updatedLoyaltyPoints());
    return bookingForm;
  }

  public static BookingForm from(Booking booking) {
    BookingForm bookingForm = new BookingForm();
    bookingForm.setMovieGoerId(booking.movieGoerId());
    bookingForm.setMovieName(booking.filmName());
    bookingForm.setScheduleDate(booking.bookingTime());
    bookingForm.setScheduleId(booking.scheduleId());
    bookingForm.setNumberOfSeats(booking.numberOfSeatsBooked());
    bookingForm.setSeatPrice(booking.seatPrice());

    return bookingForm;
  }
}

```
- created issue for handling concurrency for normal booking

### 31/3/23
- resolved issues for handling concurrency
- noticed logic in the Controller and discussed possibility of making the service adaptable for API as well as MVC
- Looked ways to move more logic into the domain

```java
@Controller
public class MovieController {
  @PostMapping("/book")
  public String makeBooking(@ModelAttribute("bookingForm") BookingForm bookingForm) {
    MovieProgram movieProgram = movieService.findMovieProgramBy(bookingForm.getScheduleId());
    Booking booking = BookingForm.to(bookingForm, movieProgram);
    Payment payment = BookingForm.toPayment(bookingForm);
    BookingOutcome notification = bookingService.save(booking, payment);

    if (notification.isSuccess()) {
      return "redirect:/bookings";
    } else {
      return "redirect:/seatsNotAvailable";
    }
  }
}
```
- Can we remove references to the repositories in the controller
- Is there any opportunity for refactoring?
- Are things in the right place?
- Can we add an API instead of using MVC?

### 1/4/23
- Interesting discussion about Command Query Separation (CQS)
- We wanted to move decision logic into the service layer so that it could be shared between MVC and SPA type approaches
  - this required returning a result or notification from operations
  - we spoke about whether this violated the CQS rule
  - we discussed the approach of using exceptions as an alternative
  - Martin Fowler article was helpful to show errors in a notification result
    - https://martinfowler.com/articles/replaceThrowWithNotification.html
  - we used this approach for our problem

```java
@Controller
public class MovieController {
  @PostMapping("/book")
  public String makeBooking(@ModelAttribute("bookingForm") BookingForm bookingForm) {
    MovieProgram movieProgram = movieService.findMovieProgramBy(bookingForm.getScheduleId());
    Booking booking = BookingForm.to(bookingForm, movieProgram);
    Payment payment = BookingForm.toPayment(bookingForm);
    Notification notification = bookingService.payForBooking(booking, payment);

    if (notification.isSuccess()) {
      return "redirect:/bookings";
    } else {
      return "redirect:/seatsNotAvailable";
    }
  }

  @PostMapping("/amendBooking")
  public String makeAmendBooking(@ModelAttribute("amendBookingForm") AmendBookingForm amendBookingForm,
                                 @RequestParam("bookingId") Long bookingId,
                                 @RequestParam("additionalSeats") int additionalSeats) {
    Payment payment = AmendBookingForm.toPayment(amendBookingForm);
    Notification notification = bookingService.amendBooking(bookingId, additionalSeats, payment);

    if(notification.isSuccess()) {
      return "redirect:/bookings";
    } else {
      return "redirect:/seatsNotAvailable?bookingId=" + bookingId;
    }
  }
}
```
The BookingService functions:
```java
public class BookingService {
  public Notification payForBooking(Booking booking, Payment payment) {
    MovieProgram movieProgram = booking.movieProgram();
    Notification notification = new Notification();
    if (!movieProgram.seatsAvailableFor(booking.numberOfSeatsBooked())) {
      notification.addError("No seats available");
      return notification;
    }

    MovieGoer movieGoer = movieGoerRepository.findById(booking.movieGoerId())
            .orElseThrow(IllegalArgumentException::new);
    movieGoer.confirmPayment(payment);
    movieGoerRepository.save(movieGoer);
    Booking savedBooking = bookingRepository.save(booking);
    payment.associateBooking(savedBooking);
    paymentRepository.save(payment);

    return notification;
  }
  
  public Notification amendBooking(Long bookingId, int additionalSeats, Payment payment) {
    Notification notification = new Notification();

    Booking booking = bookingRepository.findById(bookingId).orElseThrow(IllegalArgumentException::new);
    boolean seatsAvailable = booking.movieProgram().seatsAvailableFor(additionalSeats);

    if (!seatsAvailable) {
      notification.addError("Seats not available");
      return notification;
    }

    booking.addSeats(additionalSeats);
    MovieGoer movieGoer = movieGoerRepository.findById(booking.movieGoerId())
            .orElseThrow(IllegalArgumentException::new);
    movieGoer.confirmPayment(payment);
    movieGoerRepository.save(movieGoer);
    Booking savedBooking = bookingRepository.save(booking);
    payment.associateBooking(savedBooking);
    paymentRepository.save(payment);

    return notification;
  }
  
}

```

In the next session we want to add custom Id types with validation:
```java
@Controller
public class MovieController {
  @GetMapping("/seatsNotAvailable")
  public String seatsNotAvailable(Model model, @RequestParam(value = "bookingId", defaultValue = "-1") Long bookingId) {
    // TODO: We could add a BookingId class which would contain validation
    if (bookingId != -1) {
      Booking booking = bookingService.findBooking(bookingId);
      BookingForm bookingForm = BookingForm.from(booking);
      model.addAttribute("bookingForm", bookingForm);
    }

    model.addAttribute("movieGoer", movieGoerView());
    return "/bookings/seatsNotAvailable";
  }
}
```

### 13/4/23 
- Discussed bounded contexts and how to split the admin and moviegoer bounded contexts
- Spring is outside in moviebooker
- There are now separate folders for moviegoer and admin bounded context
- We have decided not to use microservices or separate projects for the moment.
- There may be some duplication of classes and we haven't decided how to deal with this
- Choices include:
  - expose functionality from one bounded context to the other
  - two separate ways of reading from the database for each context

### 13/4/23 - Tom
Aim: Create page for listing movie programs for Admin
Completed admin page for listing programs

### 14/4/23
- Show movie name on list of programs
- button for add new program
  - validation: for movie, chosen time etc.
- later: list of movies
  - form for a new movie
- Ideas:
  - 15 minutes between movies
  - what is already scheduled?

### 17/4/23
- Added movie name and price to list of programs
- button for add new program (style)
- button for add new program - added get mapping

### 18/4/23 Dmitri - Tom
- Added validation
- Now add list of movies
  - form for a new movie

### 20/6/23 Dmitri - Tom
Current issue with login
- redirect goes to original inputted url or root
- We need a default redirect from root to the correct url for accessing the application
- We also need a 404 page for unsupported pages

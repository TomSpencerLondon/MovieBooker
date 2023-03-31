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
    BookingOutcome bookingOutcome = bookingService.save(booking, payment);

    if (bookingOutcome.isSuccess()) {
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
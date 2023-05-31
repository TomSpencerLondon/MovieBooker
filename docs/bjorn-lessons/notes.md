### Lesson notes

#### 23 May 2023
Potential improvements:
- Move docker command to docker compose
- Mismatch between film id and booking id on the bookings page

#### 24 May 2023
Improvements:
- delete autowired in admincontroller and put the services directly into constructor
- format the /admin/movie-programs times
  - https://www.baeldung.com/dates-in-thymeleaf

Extra suggestions:
- Localstack for S3 storing images test
- https://localstack.cloud/
- https://docs.localstack.cloud/overview/
- How to refer to image name in database - dynamic image name

domain driven design:
- core - usp - unique selling proposition - make application different
  - cinema management - movies + cinemas
- supporting
  - anything that is required to perform business - but not core business
  - loyalty card - not required for core business but necessary to support business
- generic 
  - supports business but generic to any business
  - invoicing

This function:
```java
public class BookingService {
    private BookingRepository bookingRepository;
    private MovieProgramRepository movieProgramRepository;

    private MovieGoerRepository movieGoerRepository;

    private PaymentRepository paymentRepository;

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
}
```
is different to addSeatsToCard:
```java
public class LoyaltyCard implements LoyaltyDevice {
    public static final int LOYALTY_POINTS_PER_SEAT = 5;

    private int updatedLoyaltyPoints;
    private int loyaltySeats;

    public LoyaltyCard(MovieGoer movieGoer) {
        this.updatedLoyaltyPoints = movieGoer.loyaltyPoints();
        loyaltySeats = 0;
    }

    public void addSeatsToCard(int seats) {
        for (int i = 0; i < seats; i++) {
            if (updatedLoyaltyPoints >= LOYALTY_POINTS_PER_SEAT) {
                loyaltySeats++;
                updatedLoyaltyPoints = updatedLoyaltyPoints - LOYALTY_POINTS_PER_SEAT;
                continue;
            }
            updatedLoyaltyPoints++;
        }
    }

    @Override
    public int loyaltySeats() {
        return loyaltySeats;
    }

    @Override
    public int updatedLoyaltyPoints() {
        return updatedLoyaltyPoints;
    }
}
```
Because the service method involves several entities and more high level business logic. The loyalty just concerns the loyalty card entity itself.

### 31 May 2023
- to upload an image we need type=file on the form input
- we can use reset for cancel (input or button type reset)
  https://www.baeldung.com/spring-boot-thymeleaf-image-upload
- Java IO and Java next generation io
- Stereotypes - Spring - extension of dependency injection
  - @Component + autowire: @Service, @Controller, @Repository inherited from @Component
  - Document source code - not generic @Component
  - Spring also adds features at runtime
  - @Repository - JPA looks for these + adds additional data access code at runtime
    - Allows findByName orderBy method names to define SQL by method names
- organize pom file: test, database
- Lombok - @RequiredArgsConstructor = required arguments all defined as final
- Try to finish upload for images + display uploaded image

commands to list buckets and view objects in bucket:
```bash
tom@tom-ubuntu:~/Projects/MovieBooker/script/localstack/s3$ aws --endpoint-url=http://localhost:4566 --region=us-east-1 s3 ls
2023-05-31 16:33:36 movie-images-s3-bucket
tom@tom-ubuntu:~/Projects/MovieBooker/script/localstack/s3$ aws --endpoint-url=http://localhost:4566 --region=us-east-1 s3 ls movie-images-s3-bucket
2023-05-31 16:34:21     182215 tom_spencer.jpg
```
download is similar:
```bash
tom@tom-ubuntu:~/Projects/MovieBooker/script/localstack/s3$ aws --endpoint-url=http://localhost:4566 --region=us-east-1 s3 cp s3://movie-images-s3-bucket/tom_spencer.jpg ~/Desktop/tom.jpg
download: s3://movie-images-s3-bucket/tom_spencer.jpg to ../../../../../Desktop/tom.jpg
```
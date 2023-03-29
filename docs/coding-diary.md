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


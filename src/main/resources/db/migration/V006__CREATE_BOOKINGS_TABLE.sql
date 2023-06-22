CREATE TABLE bookings
(
    booking_id             BIGINT  NOT NULL AUTO_INCREMENT,
    schedule_id            BIGINT  NULL,
    user_id                BIGINT  NULL,
    number_of_seats_booked INT     NULL,
    seat_price             DECIMAL NULL,
    CONSTRAINT pk_bookings PRIMARY KEY (booking_id)
);

ALTER TABLE bookings
    ADD CONSTRAINT FK_BOOKINGS_ON_SCHEDULE FOREIGN KEY (schedule_id) REFERENCES movie_programs (schedule_id);
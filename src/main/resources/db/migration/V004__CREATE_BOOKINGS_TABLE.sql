CREATE TABLE bookings
(
    booking_id             BIGINT  NOT NULL AUTO_INCREMENT,
    schedule_id            BIGINT  NOT NULL,
    user_id                BIGINT  NOT NULL,
    number_of_seats_booked INT     NOT NULL,
    amount_paid             DECIMAL NOT NULL,
    updated_loyalty_points INT     NOT NULL,
    CONSTRAINT pk_bookings PRIMARY KEY (booking_id)
);

ALTER TABLE bookings
    ADD CONSTRAINT FK_BOOKINGS_ON_MOVIEGOERDBO_USERID FOREIGN KEY (user_id) REFERENCES moviegoers (user_id);

ALTER TABLE bookings
    ADD CONSTRAINT FK_BOOKINGS_ON_SCHEDULE FOREIGN KEY (schedule_id) REFERENCES movie_programs (schedule_id);
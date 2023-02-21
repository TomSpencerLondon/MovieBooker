CREATE TABLE bookings
(
    booking_id             BIGINT  NOT NULL AUTO_INCREMENT,
    schedule_id            BIGINT  NULL,
    user_id                BIGINT  NULL,
    number_of_seats_booked INT     NULL,
    price                  DECIMAL NULL,
    movie_goer_dbo_user_id BIGINT  NOT NULL,
    CONSTRAINT pk_bookings PRIMARY KEY (booking_id)
);

ALTER TABLE bookings
    ADD CONSTRAINT FK_BOOKINGS_ON_MOVIEGOERDBO_USERID FOREIGN KEY (movie_goer_dbo_user_id) REFERENCES moviegoers (user_id);

ALTER TABLE bookings
    ADD CONSTRAINT FK_BOOKINGS_ON_SCHEDULE FOREIGN KEY (schedule_id) REFERENCES movie_programs (schedule_id);
CREATE TABLE rooms
(
    id BIGINT AUTO_INCREMENT NOT NULL,
    room_number INT NOT NULL,
    number_of_seats INT NOT NULL,
    cinema_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (cinema_id) REFERENCES cinemas (id)
);

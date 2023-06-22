CREATE TABLE screen
(
    screen_id       BIGINT NOT NULL AUTO_INCREMENT,
    name            VARCHAR(255) NOT NULL,
    number_of_seats INT NOT NULL,
    PRIMARY KEY (screen_id)
);
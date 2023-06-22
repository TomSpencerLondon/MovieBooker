CREATE TABLE movie_programs
(
    schedule_id   BIGINT   NOT NULL AUTO_INCREMENT,
    price         DECIMAL  NULL,
    schedule_date datetime NULL,
    screen_id     BIGINT   NULL,
    movie_id      BIGINT   NULL,
    CONSTRAINT pk_movie_programs PRIMARY KEY (schedule_id),
    FOREIGN KEY (movie_id) REFERENCES movies (movie_id),
    FOREIGN KEY (screen_id) REFERENCES screen (screen_id)
);
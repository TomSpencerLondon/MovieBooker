CREATE TABLE movie_programs
(
    schedule_id   BIGINT   NOT NULL AUTO_INCREMENT,
    price         DECIMAL  NULL,
    schedule_date datetime NULL,
    seats         INT      NULL,
    movie_id      BIGINT   NULL,
    CONSTRAINT pk_movie_programs PRIMARY KEY (schedule_id)
);

ALTER TABLE movie_programs
    ADD CONSTRAINT FK_MOVIE_PROGRAMS_ON_MOVIE FOREIGN KEY (movie_id) REFERENCES movies (movie_id);
CREATE TABLE movies
(
    movie_id      BIGINT        NOT NULL AUTO_INCREMENT,
    movie_name    VARCHAR(255)  NULL,
    movie_image   VARCHAR(255)  NULL,
    release_date  date          NULL,
    `description` VARCHAR(1000) NULL,
    CONSTRAINT pk_movies PRIMARY KEY (movie_id)
);
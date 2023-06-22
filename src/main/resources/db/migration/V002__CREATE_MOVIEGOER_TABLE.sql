CREATE TABLE moviegoer
(
    moviegoer_id      BIGINT                              NOT NULL AUTO_INCREMENT,
    loyalty_points    INT                                 NULL,
    is_loyalty_user   BIT(1)                              NULL,
    asked_for_loyalty BIT(1)                              NULL,
    user_id           BIGINT                              NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(user_id),
    CONSTRAINT pk_moviegoer PRIMARY KEY (moviegoer_id)
);
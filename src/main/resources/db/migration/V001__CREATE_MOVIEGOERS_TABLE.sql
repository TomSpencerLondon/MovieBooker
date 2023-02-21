CREATE TABLE moviegoers
(
    user_id        BIGINT       NOT NULL AUTO_INCREMENT,
    user_name      VARCHAR(255) NULL,
    password       VARCHAR(255) NULL,
    loyalty_points INT          NULL,
    CONSTRAINT pk_moviegoers PRIMARY KEY (user_id)
);

ALTER TABLE moviegoers
    ADD CONSTRAINT uc_b7e0f316e70d53ae7b345573a UNIQUE (user_name);

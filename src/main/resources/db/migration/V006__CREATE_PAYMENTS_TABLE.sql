CREATE TABLE payments
(
    id             BIGINT AUTO_INCREMENT NOT NULL,
    loyalty_points INT                   NOT NULL,
    booking_id     BIGINT                NOT NULL,
    amount         DECIMAL               NOT NULL,
    date           datetime              NOT NULL,
    CONSTRAINT pk_payments PRIMARY KEY (id)
);
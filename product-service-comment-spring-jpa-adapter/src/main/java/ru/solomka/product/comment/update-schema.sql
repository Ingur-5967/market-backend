CREATE TABLE comments
(
    id         UUID             NOT NULL,
    user_id    UUID             NOT NULL,
    product_id UUID             NOT NULL,
    comment    VARCHAR(255)     NOT NULL,
    rating     DOUBLE PRECISION NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_comments PRIMARY KEY (id)
);
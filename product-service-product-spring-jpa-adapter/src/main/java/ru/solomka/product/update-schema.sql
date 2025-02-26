CREATE TABLE products
(
    id          UUID                        NOT NULL,
    name        VARCHAR(50)                 NOT NULL,
    description VARCHAR(250)                NOT NULL,
    price       INTEGER                     NOT NULL,
    rating      DOUBLE PRECISION,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_products PRIMARY KEY (id)
);
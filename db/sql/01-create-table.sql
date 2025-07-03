USE book_manager;

CREATE TABLE user
(
    id        BIGINT       NOT NULL,
    email     VARCHAR(256) NOT NULL,
    password  VARCHAR(128) NOT NULL,
    name      VARCHAR(32)  NOT NULL,
    role_type ENUM ('ADMIN', 'USER'),
    PRIMARY KEY (id)
);

CREATE TABLE book
(
    id           BIGINT       NOT NULL,
    title        VARCHAR(128) NOT NULL,
    author       VARCHAR(32)  NOT NULL,
    release_date DATE         NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE rental
(
    book_id         BIGINT   NOT NULL,
    user_id         BIGINT   NOT NULL,
    rental_datetime DATETIME NOT NULL,
    return_deadline DATETIME NOT NULL,
    FOREIGN KEY (book_id) REFERENCES book (id),
    FOREIGN KEY (user_id) REFERENCES user (id)
)
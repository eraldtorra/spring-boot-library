CREATE TABLE book
(
    id               BIGINT AUTO_INCREMENT NOT NULL,
    title            VARCHAR(255) NULL,
    author           VARCHAR(255) NULL,
    `description`    VARCHAR(255) NULL,
    copies           INT NULL,
    copies_available INT NULL,
    category         VARCHAR(255) NULL,
    img              VARCHAR(255) NULL,
    CONSTRAINT pk_book PRIMARY KEY (id)
);

CREATE TABLE checkout
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    user_email    VARCHAR(255) NULL,
    checkout_date VARCHAR(255) NULL,
    return_date   VARCHAR(255) NULL,
    book_id       BIGINT NULL,
    CONSTRAINT pk_checkout PRIMARY KEY (id)
);

CREATE TABLE forum
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    content      VARCHAR(255) NULL,
    posted_by    VARCHAR(255) NULL,
    posting_date datetime NULL,
    posted_in    BIGINT NOT NULL,
    CONSTRAINT pk_forum PRIMARY KEY (id)
);

CREATE TABLE `history`
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    user_email    VARCHAR(255) NULL,
    checkout_date VARCHAR(255) NULL,
    returned_date VARCHAR(255) NULL,
    title         VARCHAR(255) NULL,
    author        VARCHAR(255) NULL,
    `description` VARCHAR(255) NULL,
    img           VARCHAR(255) NULL,
    CONSTRAINT pk_history PRIMARY KEY (id)
);

CREATE TABLE messages
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    user_email  VARCHAR(255) NULL,
    title       VARCHAR(255) NULL,
    question    VARCHAR(255) NULL,
    admin_email VARCHAR(255) NULL,
    response    VARCHAR(255) NULL,
    closed      BIT(1) NULL,
    CONSTRAINT pk_messages PRIMARY KEY (id)
);

CREATE TABLE payment
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    user_email VARCHAR(255) NULL,
    amount DOUBLE NULL,
    CONSTRAINT pk_payment PRIMARY KEY (id)
);

CREATE TABLE review
(
    id                 BIGINT AUTO_INCREMENT NOT NULL,
    user_email         VARCHAR(255) NULL,
    date               datetime NULL,
    rating DOUBLE NULL,
    book_id            BIGINT NULL,
    review_description VARCHAR(255) NULL,
    CONSTRAINT pk_review PRIMARY KEY (id)
);

CREATE TABLE threads
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    title         VARCHAR(255) NULL,
    created_by    VARCHAR(255) NULL,
    creation_date datetime NULL,
    CONSTRAINT pk_threads PRIMARY KEY (id)
);

ALTER TABLE forum
    ADD CONSTRAINT FK_FORUM_ON_POSTED_IN FOREIGN KEY (posted_in) REFERENCES threads (id);
CREATE TABLE book
(
    id               BIGINT AUTO_INCREMENT NOT NULL,
    title            VARCHAR(255)          NULL,
    author           VARCHAR(255)          NULL,
    `description`    VARCHAR(255)          NULL,
    copies           INT                   NULL,
    copies_available INT                   NULL,
    category         VARCHAR(255)          NULL,
    img              VARCHAR(255)          NULL,
    CONSTRAINT pk_book PRIMARY KEY (id)
);

CREATE TABLE checkout
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    user_email    VARCHAR(255)          NULL,
    checkout_date VARCHAR(255)          NULL,
    return_date   VARCHAR(255)          NULL,
    book_id       BIGINT                NULL,
    CONSTRAINT pk_checkout PRIMARY KEY (id)
);

CREATE TABLE forum
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    content      VARCHAR(255)          NULL,
    posted_by    VARCHAR(255)          NULL,
    posting_date datetime              NULL,
    posted_in    BIGINT                NOT NULL,
    CONSTRAINT pk_forum PRIMARY KEY (id)
);

CREATE TABLE `history`
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    user_email    VARCHAR(255)          NULL,
    checkout_date VARCHAR(255)          NULL,
    returned_date VARCHAR(255)          NULL,
    title         VARCHAR(255)          NULL,
    author        VARCHAR(255)          NULL,
    `description` VARCHAR(255)          NULL,
    img           VARCHAR(255)          NULL,
    CONSTRAINT pk_history PRIMARY KEY (id)
);

CREATE TABLE messages
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    user_email  VARCHAR(255)          NULL,
    title       VARCHAR(255)          NULL,
    question    VARCHAR(255)          NULL,
    admin_email VARCHAR(255)          NULL,
    response    VARCHAR(255)          NULL,
    closed      BIT(1)                NULL,
    CONSTRAINT pk_messages PRIMARY KEY (id)
);

CREATE TABLE payment
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    user_email VARCHAR(255)          NULL,
    amount     DOUBLE                NULL,
    CONSTRAINT pk_payment PRIMARY KEY (id)
);

CREATE TABLE review
(
    id                 BIGINT AUTO_INCREMENT NOT NULL,
    user_email         VARCHAR(255)          NULL,
    date               datetime              NULL,
    rating             DOUBLE                NULL,
    book_id            BIGINT                NULL,
    review_description VARCHAR(255)          NULL,
    CONSTRAINT pk_review PRIMARY KEY (id)
);

CREATE TABLE threads
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    title         VARCHAR(255)          NULL,
    created_by    VARCHAR(255)          NULL,
    creation_date datetime              NULL,
    CONSTRAINT pk_threads PRIMARY KEY (id)
);

ALTER TABLE forum
    ADD CONSTRAINT FK_FORUM_ON_POSTED_IN FOREIGN KEY (posted_in) REFERENCES threads (id);
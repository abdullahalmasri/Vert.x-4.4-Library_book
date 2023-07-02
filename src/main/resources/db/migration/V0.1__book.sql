CREATE TABLE IF NOT EXISTS library
(
    id UUID NOT NULL
    , name VARCHAR(255) NOT NULL
    , PRIMARY KEY(id)
    );

CREATE TABLE IF NOT EXISTS books (
    id         UUID       NOT NULL
    , author     VARCHAR(255) NOT NULL
    , country    VARCHAR(255)
    , image_link VARCHAR(255)
    , language   VARCHAR(255)
    , link       VARCHAR(255)
    , pages      INT4
    , title      VARCHAR(255) NOT NULL
    , year       INT4
    , library_id UUID NOT NULL
    , PRIMARY KEY(id)
    , FOREIGN KEY(library_id) REFERENCES library(id)
    );

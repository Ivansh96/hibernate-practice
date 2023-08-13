CREATE TABLE users
(
    id         UUID NOT NULL PRIMARY KEY,
    username   VARCHAR(128) UNIQUE,
    firstname  VARCHAR(128),
    lastname   VARCHAR(128),
    birth_date DATE,
    company_id UUID REFERENCES company (id)
);

CREATE TABLE company
(
    id   UUID NOT NULL PRIMARY KEY,
    name VARCHAR(64)
);

CREATE TABLE profile
(
  id UUID PRIMARY KEY,
  user_id UUID NOT NULL UNIQUE REFERENCES users (id),
  street VARCHAR(128),
  language CHAR(2)
);

CREATE TABLE company_locale
(
    company_id UUID NOT NULL REFERENCES company (id),
    lang CHAR(2) NOT NULL,
    description VARCHAR(128) NOT NULL,
    PRIMARY KEY (company_id, lang)
);




CREATE EXTENSION IF NOT EXISTS "uuid-ossp";


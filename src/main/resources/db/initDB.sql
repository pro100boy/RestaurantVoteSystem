DROP TABLE user_roles IF EXISTS;
DROP TABLE votes IF EXISTS;
DROP TABLE menus IF EXISTS;
DROP TABLE users IF EXISTS;
DROP TABLE restaurants IF EXISTS;
DROP SEQUENCE global_seq IF EXISTS;

CREATE SEQUENCE GLOBAL_SEQ AS INTEGER START WITH 100000;

CREATE TABLE users
(
  id         INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
  name       VARCHAR(255) NOT NULL,
  email      VARCHAR(255) NOT NULL,
  password   VARCHAR(255) NOT NULL,
  registered TIMESTAMP,
  enabled    BOOLEAN   DEFAULT TRUE,
  CONSTRAINT users_unique_email_idx UNIQUE (email)
);

CREATE TABLE user_roles
(
  user_id INTEGER NOT NULL,
  role    VARCHAR(255),
  CONSTRAINT user_roles_idx UNIQUE (user_id, role),
  FOREIGN KEY ( user_id ) REFERENCES USERS (id) ON DELETE CASCADE
);

CREATE TABLE restaurants (
  id          INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
  name        VARCHAR(255) NOT NULL,
  description VARCHAR(255) NOT NULL
);

CREATE TABLE votes (
  id        INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
  user_id   INTEGER NOT NULL,
  rest_id   INTEGER NOT NULL,
  vote_date DATE NOT NULL,
  CONSTRAINT user_date_restaurant_unique_idx UNIQUE (user_id, rest_id, vote_date),
  FOREIGN KEY (rest_id) REFERENCES restaurants (id) ON DELETE CASCADE,
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE menus (
  id        INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
  rest_id   INTEGER      NOT NULL,
  menu_date DATE NOT NULL,
  name      VARCHAR(255) NOT NULL,
  price     REAL  DEFAULT 0,
  CONSTRAINT date_restaurant_unique_idx UNIQUE (rest_id, menu_date),
  FOREIGN KEY (rest_id) REFERENCES restaurants (id) ON DELETE CASCADE
);

/*<create index statement> ::= CREATE INDEX [ IF NOT EXISTS ] <index name> ON <table name> <left paren> {<column name> [ASC | DESC]}, ... <right paren>*/
CREATE UNIQUE INDEX users_unique_email_idx ON USERS (email);
CREATE INDEX fki_menus_rest ON menus (rest_id);
CREATE INDEX fki_votes_rest ON votes (rest_id);
CREATE INDEX fki_votes_user ON votes (user_id);
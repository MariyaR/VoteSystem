DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS voting_history;
DROP TABLE IF EXISTS restos_history;
DROP TABLE IF EXISTS restos;
DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START WITH 100000;

CREATE TABLE users
(
  id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  name             VARCHAR                 NOT NULL,
  email            VARCHAR                 NOT NULL,
  password         VARCHAR                 NOT NULL,
  registered       TIMESTAMP DEFAULT now() NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx ON users (email);

CREATE TABLE user_roles
(
  user_id INTEGER NOT NULL,
  role    VARCHAR,
  CONSTRAINT user_roles_idx UNIQUE (user_id, role),
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE restos (
  id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  name             VARCHAR                 NOT NULL,
  address          VARCHAR                 NOT NULL
);
CREATE UNIQUE INDEX users_unique_address_idx
  ON restos (address);

CREATE TABLE voting_history
(
  id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  date             DATE DEFAULT current_date NOT NULL,
  user_id          INTEGER           NOT NULL,
  resto_id         INTEGER           NOT NULL,

  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
  FOREIGN KEY (resto_id) REFERENCES restos (id) ON DELETE CASCADE,
  CONSTRAINT date_user_idx UNIQUE (date, user_id)
);

CREATE TABLE restos_history (
  id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  date             DATE DEFAULT now() NOT NULL,
  resto_id         INTEGER           NOT NULL,
  resto_menu       VARCHAR           NOT NULL,
  counter          INTEGER,

    FOREIGN KEY (resto_id) REFERENCES restos (id) ON DELETE CASCADE,
  CONSTRAINT date_resto_idx UNIQUE (date, resto_id)

);

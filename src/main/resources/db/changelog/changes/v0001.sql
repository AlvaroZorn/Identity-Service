create table "user" (
  ID SERIAL PRIMARY KEY,
  USERNAME TEXT NOT NULL,
  PASSWORD TEXT NOT NULL,
  EMAIL TEXT NOT NULL,
  CREATED TIMESTAMP NOT NULL,
  UPDATED TIMESTAMP,
  ENABLED BOOLEAN
);

create table "token" (
  ID SERIAL PRIMARY KEY,
  TOKEN TEXT NOT NULL,
  EXPIRY_DATE TIMESTAMP,
  USER_ID INT NOT NULL
);
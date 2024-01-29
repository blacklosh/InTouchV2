CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

create table user_accounts
(
    id       uuid not null
        constraint user_account_pk
            primary key,
    name     varchar(30),
    email    varchar(30),
    password_hash varchar(30)
);

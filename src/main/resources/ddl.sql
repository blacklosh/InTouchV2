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

create table posts
(
    id uuid constraint posts_pk primary key,
    author_id uuid constraint posts_user_accounts_id_fk
            references user_accounts,
    text varchar(500) not null
);

create table message
(
    id uuid primary key ,
    creation_date timestamp(6),
    author_id uuid,
    foreign key (author_id) references user_accounts(id),
    text varchar(500)
);

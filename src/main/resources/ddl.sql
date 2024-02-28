CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

create table if not exists file_info
(
    id uuid primary key,
    original_file_name varchar(100),
    storage_file_name varchar(100) not null,
    size bigint not null,
    type varchar(100)
);

create table if not exists user_accounts
(
    id       uuid not null
        constraint user_account_pk
            primary key,
    name     varchar(30),
    email    varchar(30),
    password_hash varchar(30),
    avatar_id     uuid
        references file_info
);

create table if not exists posts
(
    id uuid constraint posts_pk primary key,
    author_id uuid constraint posts_user_accounts_id_fk
            references user_accounts,
    text varchar(500) not null,
    creation_date timestamp
);
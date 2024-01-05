create table users
(
    id          serial
        constraint users_pk
            primary key,
    nome        text not null,
    cognome     text not null,
    username    text not null
        constraint users_pk_2
            unique,
    mail        text not null
        constraint users_pk_3
            unique,
    password    text not null,
    role        text not null,
    insert_time timestamp default CURRENT_TIMESTAMP
);

alter table users
    owner to postgres;


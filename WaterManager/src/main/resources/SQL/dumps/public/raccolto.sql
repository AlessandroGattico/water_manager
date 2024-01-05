create table raccolto
(
    id          serial
        constraint raccolto_pk
            primary key,
    nome        text not null
        constraint raccolto_pk_2
            unique,
    insert_time timestamp default CURRENT_TIMESTAMP
);

alter table raccolto
    owner to postgres;


create table irrigazione
(
    id          serial
        constraint irrigazione_pk
            primary key,
    nome        text not null
        constraint irrigazione_pk_2
            unique,
    insert_time timestamp default CURRENT_TIMESTAMP
);

alter table irrigazione
    owner to postgres;


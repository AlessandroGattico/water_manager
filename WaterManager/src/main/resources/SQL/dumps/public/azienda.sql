create table azienda
(
    id          serial
        constraint azienda_pk
            primary key,
    nome        text not null
        constraint azienda_pk_2
            unique,
    insert_time timestamp default CURRENT_TIMESTAMP,
    id_user     serial
        constraint azienda_users_id_fk
            references users
            on delete cascade
);

alter table azienda
    owner to postgres;


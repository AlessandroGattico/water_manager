create table bacino
(
    id          serial
        constraint bacino_pk
            primary key,
    nome        text not null,
    id_user     serial
        constraint bacino_users_id_fk
            references users
            on delete cascade,
    insert_time timestamp default CURRENT_TIMESTAMP
);

alter table bacino
    owner to postgres;


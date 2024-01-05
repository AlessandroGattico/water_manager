create table approvazione
(
    id           serial
        constraint approvazione_pk
            primary key,
    id_richiesta serial
        constraint approvazione_richiesta_id_fk
            references richiesta
            on delete cascade,
    id_gestore   serial
        constraint approvazione_users_id_fk
            references users
            on delete cascade,
    approvato    boolean not null,
    date         date    not null,
    insert_time  timestamp default CURRENT_TIMESTAMP
);

alter table approvazione
    owner to postgres;


create table richiesta
(
    id              serial
        constraint richiesta_pk
            primary key,
    quantita        numeric not null,
    id_coltivazione serial
        constraint richiesta_coltivazione_id_fk
            references coltivazione
            on delete cascade,
    id_bacino       serial
        constraint richiesta_bacino_id_fk
            references bacino
            on delete cascade,
    date            date    not null,
    insert_time     timestamp default CURRENT_TIMESTAMP
);

alter table richiesta
    owner to postgres;


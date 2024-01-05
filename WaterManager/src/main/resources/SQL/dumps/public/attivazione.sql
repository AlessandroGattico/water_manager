create table attivazione
(
    id           serial
        constraint attivazione_pk
            primary key,
    current      boolean not null,
    previous     boolean not null,
    time         date    not null,
    id_attuatore serial
        constraint attivazione_attuatore_id_fk
            references attuatore
            on delete cascade,
    insert_time  timestamp default CURRENT_TIMESTAMP
);

alter table attivazione
    owner to postgres;


create table risorsa_bacino
(
    id            serial
        constraint risorsa_bacino_pk
            primary key,
    consumo       numeric not null,
    disponibilita numeric not null,
    time          date    not null,
    id_bacino     serial
        constraint risorsa_bacino_bacino_id_fk
            references bacino
            on delete cascade,
    insert_time   timestamp default CURRENT_TIMESTAMP
);

alter table risorsa_bacino
    owner to postgres;


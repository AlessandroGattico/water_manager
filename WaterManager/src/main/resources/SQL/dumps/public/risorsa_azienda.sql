create table risorsa_azienda
(
    id            serial
        constraint risorsa_azienda_pk
            primary key,
    consumo       numeric not null,
    disponibilita numeric not null,
    time          date    not null,
    id_azienda    serial
        constraint risorsa_azienda_azienda_id_fk
            references azienda
            on delete cascade,
    insert_time   timestamp default CURRENT_TIMESTAMP
);

alter table risorsa_azienda
    owner to postgres;


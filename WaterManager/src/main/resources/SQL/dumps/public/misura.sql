create table misura
(
    id          serial
        constraint misura_pk
            primary key,
    value       numeric,
    time        date not null,
    id_sensore  serial
        constraint misura_sensore_id_fk
            references sensore
            on delete cascade,
    insert_time timestamp default CURRENT_TIMESTAMP
);

alter table misura
    owner to postgres;


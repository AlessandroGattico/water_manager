create table sensore
(
    id          serial
        constraint sensore_pk
            primary key,
    nome        text not null,
    type        text not null
        constraint sensore_sensor_type_nome_fk
            references sensor_type (type)
            on delete cascade,
    id_campo    serial
        constraint sensore_campo_id_fk
            references campo
            on delete cascade,
    insert_time timestamp default CURRENT_TIMESTAMP,
    constraint sensore_pk_2
        unique (id_campo, nome)
);

alter table sensore
    owner to postgres;


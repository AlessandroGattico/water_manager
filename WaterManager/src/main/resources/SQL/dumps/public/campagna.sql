create table campagna
(
    id          serial
        constraint campagna_pk
            primary key,
    nome        text not null,
    id_azienda  serial
        constraint campagna_azienda_id_fk
            references azienda
            on delete cascade,
    insert_time timestamp default CURRENT_TIMESTAMP,
    constraint campagna_pk_2
        unique (id_azienda, nome)
);

alter table campagna
    owner to postgres;


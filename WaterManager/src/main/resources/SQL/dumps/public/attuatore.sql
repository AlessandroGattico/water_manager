create table attuatore
(
    id          serial
        constraint attuatore_pk
            primary key,
    nome        text not null,
    id_campo    serial
        constraint attuatore_campo_id_fk
            references campo
            on delete cascade,
    insert_time timestamp default CURRENT_TIMESTAMP,
    constraint attuatore_pk_2
        unique (id_campo, nome)
);

alter table attuatore
    owner to postgres;


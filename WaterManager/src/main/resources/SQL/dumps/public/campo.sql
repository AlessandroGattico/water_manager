create table campo
(
    id          serial
        constraint campo_pk
            primary key,
    nome        text not null,
    id_campagna serial
        constraint campo_campagna_id_fk
            references campagna
            on delete cascade,
    insert_time timestamp default CURRENT_TIMESTAMP
);

alter table campo
    owner to postgres;


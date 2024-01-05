create table coltivazione
(
    id          serial
        constraint coltivazione_pk
            primary key,
    raccolto    text not null
        constraint coltivazione_raccolto_nome_fk
            references raccolto (nome)
            on delete cascade,
    esigenza    text
        constraint coltivazione_esigenza_nome_fk
            references esigenza (nome)
            on delete cascade,
    irrigazione text
        constraint coltivazione_irrigazione_nome_fk
            references irrigazione (nome)
            on delete cascade,
    temperatura numeric,
    umidita     numeric,
    semina      date not null,
    insert_time timestamp default CURRENT_TIMESTAMP,
    id_campo    serial
        constraint coltivazione_campo_id_fk
            references campo
            on delete cascade
);

alter table coltivazione
    owner to postgres;


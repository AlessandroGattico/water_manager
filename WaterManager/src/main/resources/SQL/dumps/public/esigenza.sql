create table esigenza
(
    id          serial
        constraint esigenza_pk
            primary key,
    nome        text not null
        constraint esigenza_pk_2
            unique,
    insert_time timestamp default CURRENT_TIMESTAMP
);

alter table esigenza
    owner to postgres;


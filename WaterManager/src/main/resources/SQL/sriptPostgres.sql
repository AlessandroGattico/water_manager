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

create table irrigazione
(
    id          serial
        constraint irrigazione_pk
            primary key,
    nome        text not null
        constraint irrigazione_pk_2
            unique,
    insert_time timestamp default CURRENT_TIMESTAMP
);

alter table irrigazione
    owner to postgres;

create table raccolto
(
    id          serial
        constraint raccolto_pk
            primary key,
    nome        text not null
        constraint raccolto_pk_2
            unique,
    insert_time timestamp default CURRENT_TIMESTAMP
);

alter table raccolto
    owner to postgres;

create table sensor_type
(
    id          serial
        constraint sensor_type_pk
            primary key,
    type        text not null
        constraint sensor_type_pk_2
            unique,
    insert_time timestamp default CURRENT_TIMESTAMP
);

alter table sensor_type
    owner to postgres;

create table users
(
    id          serial
        constraint users_pk
            primary key,
    nome        text not null,
    cognome     text not null,
    username    text not null
        constraint users_pk_2
            unique,
    mail        text not null
        constraint users_pk_3
            unique,
    password    text not null,
    role        text not null,
    insert_time timestamp default CURRENT_TIMESTAMP
);

alter table users
    owner to postgres;

create table azienda
(
    id          serial
        constraint azienda_pk
            primary key,
    nome        text not null
        constraint azienda_pk_2
            unique,
    insert_time timestamp default CURRENT_TIMESTAMP,
    id_user     serial
        constraint azienda_users_id_fk
            references users
            on delete cascade
);

alter table azienda
    owner to postgres;

create table bacino
(
    id          serial
        constraint bacino_pk
            primary key,
    nome        text not null,
    id_user     serial
        constraint bacino_users_id_fk
            references users
            on delete cascade,
    insert_time timestamp default CURRENT_TIMESTAMP
);

alter table bacino
    owner to postgres;

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

create table richiesta
(
    id              serial
        constraint richiesta_pk
            primary key,
    quantita        numeric not null,
    id_coltivazione serial
        constraint richiesta_coltivazione_id_fk
            references coltivazione
            on delete cascade,
    id_bacino       serial
        constraint richiesta_bacino_id_fk
            references bacino
            on delete cascade,
    date            date    not null,
    insert_time     timestamp default CURRENT_TIMESTAMP
);

alter table richiesta
    owner to postgres;

create table approvazione
(
    id           serial
        constraint approvazione_pk
            primary key,
    id_richiesta serial
        constraint approvazione_richiesta_id_fk
            references richiesta
            on delete cascade,
    id_gestore   serial
        constraint approvazione_users_id_fk
            references users
            on delete cascade,
    approvato    boolean not null,
    date         date    not null,
    insert_time  timestamp default CURRENT_TIMESTAMP
);

alter table approvazione
    owner to postgres;

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


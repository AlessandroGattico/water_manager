create table esigenza
(
    id          integer not null
        constraint esigenza_pk
            primary key autoincrement,
    nome        text    not null
        constraint esigenza_pk_2
            unique,
    insert_time timestamp default current_timestamp
);

create table irrigazione
(
    id          integer not null
        constraint irrigazione_pk
            primary key autoincrement,
    nome        text    not null
        constraint irrigazione_pk_2
            unique,
    insert_time timestamp default current_timestamp
);

create table raccolto
(
    id          integer not null
        constraint raccolto_pk
            primary key autoincrement,
    nome        text    not null
        constraint raccolto_pk_2
            unique,
    insert_time timestamp default current_timestamp
);

create table sensor_type
(
    id          integer not null
        constraint sensor_type_pk
            primary key,
    type        text    not null
        constraint sensor_type_pk_2
            unique,
    insert_time timestamp default current_timestamp
);


create table users
(
    id          integer                not null
        constraint user_pk
            primary key autoincrement,
    nome        text                   not null,
    cognome     text                   not null,
    username    text                   not null
        constraint user_pk_2
            unique,
    mail        text                   not null
        constraint user_pk_3
            unique,
    password    text                   not null,
    role        text                   not null,
    insert_time timestamp default current_timestamp,
    enabled     boolean   default true not null
);

create table azienda
(
    id          integer                not null
        constraint azienda_pk
            primary key autoincrement,
    nome        text                   not null
        constraint azienda_pk_2
            unique,
    insert_time timestamp default current_timestamp,
    id_user     integer                not null
        constraint azienda_user_id_fk
            references users
            on delete cascade,
    enabled     boolean   default true not null
);

create table bacino
(
    id          integer                not null
        constraint bacino_pk
            primary key autoincrement,
    nome        text                   not null,
    id_user     integer                not null
        constraint bacino_user_id_fk
            references users
            on delete cascade,
    insert_time timestamp default current_timestamp,
    enabled     boolean   default true not null
);

create table campagna
(
    id          integer                not null
        constraint campagna_pk
            primary key autoincrement,
    nome        text                   not null,
    id_azienda  integer                not null
        constraint campagna_azienda_id_fk
            references azienda
            on delete cascade,
    insert_time timestamp default current_timestamp,
    enabled     boolean   default true not null,
    constraint campagna_pk_2
        unique (id_azienda, nome)
);

create table campo
(
    id          integer                not null
        constraint campo_pk
            primary key autoincrement,
    nome        text                   not null,
    id_campagna integer                not null
        constraint campo_campagna_id_fk
            references campagna
            on delete cascade,
    insert_time timestamp default CURRENT_TIMESTAMP,
    dimensione  double                 not null,
    enabled     boolean   default true not null
);

create table attuatore
(
    id          integer not null
        constraint attuatore_pk
            primary key autoincrement,
    nome        text    not null,
    id_campo    integer not null
        constraint attuatore_campo_id_fk
            references campo
            on delete cascade,
    insert_time timestamp default current_timestamp,
    constraint attuatore_pk_2
        unique (id_campo, nome)
);

create table actuator_topics
(
    id           integer not null
        constraint actuator_topics_pk
            primary key autoincrement,
    id_attuatore integer not null
        constraint actuator_topics_attuatore_id_fk
            references attuatore,
    topic        text    not null,
    insert_time  timestamp default current_timestamp
);

create table attivazione
(
    id           integer                 not null
        constraint attivazione_pk
            primary key autoincrement,
    current      BOOLEAN   default false not null,
    time         date                    not null,
    id_attuatore integer                 not null
        constraint attivazione_attuatore_id_fk
            references attuatore
            on delete cascade,
    insert_time  timestamp default current_timestamp
);

create table coltivazione
(
    id          integer                not null
        constraint coltivazione_pk
            primary key autoincrement,
    raccolto    text                   not null
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
    temperatura double,
    umidita     double,
    semina      date                   not null,
    insert_time timestamp default current_timestamp,
    id_campo    integer                not null
        constraint coltivazione_campo_id_fk
            references campo
            on delete cascade,
    enabled     boolean   default true not null
);

create table richiesta
(
    id              integer not null
        constraint richiesta_pk
            primary key autoincrement,
    quantita        double  not null,
    id_coltivazione integer not null
        constraint richiesta_coltivazione_id_fk
            references coltivazione
            on delete cascade,
    id_bacino       integer not null
        constraint richiesta_bacino_id_fk
            references bacino
            on delete cascade,
    date            date    not null,
    insert_time     timestamp default current_timestamp,
    nome_azienda    text    not null
        constraint richiesta___fk
            references azienda (nome)
);

create table approvazione
(
    id           integer not null
        constraint approvazione_pk
            primary key autoincrement,
    id_richiesta integer not null
        constraint approvazione_richiesta_id_fk
            references richiesta
            on delete cascade,
    id_gestore   integer not null
        references users
            on delete cascade,
    approvato    boolean not null,
    date         date    not null,
    insert_time  timestamp default current_timestamp
);

create table risorsa_azienda
(
    id            integer not null
        constraint risorsa_azienda_pk
            primary key autoincrement,
    consumo       double  not null,
    disponibilita double  not null,
    time          date    not null,
    id_azienda    integer not null
        constraint risorsa_azienda_azienda_id_fk
            references azienda
            on delete cascade,
    insert_time   timestamp default current_timestamp
);

create table risorsa_bacino
(
    id            integer not null
        constraint risorsa_bacino_pk
            primary key autoincrement,
    consumo       double  not null,
    disponibilita double  not null,
    time          date    not null,
    id_bacino     integer not null
        constraint risorsa_bacino_bacino_id_fk
            references bacino
            on delete cascade,
    insert_time   timestamp default current_timestamp
);

create table sensore
(
    id          integer                not null
        constraint sensore_pk
            primary key,
    nome        text                   not null,
    type        text                   not null
        constraint sensore_sensor_type_nome_fk
            references sensor_type (nome)
            on delete cascade,
    id_campo    integer                not null
        constraint sensore_campo_id_fk
            references campo
            on delete cascade,
    insert_time timestamp default current_timestamp,
    enabled     boolean   default true not null,
    constraint sensore_pk_2
        unique (id_campo, nome)
);

create table misura
(
    id          integer not null
        constraint misura_pk
            primary key autoincrement,
    value       double,
    time        date    not null,
    id_sensore  integer not null
        constraint misura_sensore_id_fk
            references sensore
            on delete cascade,
    insert_time timestamp default current_timestamp
);

create table waiting_resource_azienda
(
    id           integer not null
        constraint waiting_resource_azienda_pk
            primary key
                on conflict rollback,
    id_azienda   integer not null
        constraint waiting_resource_azienda_azienda_id_fk
            references azienda,
    id_richiesta integer not null
        constraint waiting_resource_azienda_richiesta_id_fk
            references richiesta,
    insert_time  timestamp default current_timestamp
);


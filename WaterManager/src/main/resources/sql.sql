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
create table Azienda
(
    UUID INTEGER not null
        constraint Azienda_pk
            primary key,
    Nome TEXT    not null
        unique
);

create table BacinoIdrico
(
    UUID INT  not null
        constraint BacinoIdrico_pk
            primary key,
    Nome TEXT not null
        unique
);

create table Campagna
(
    UUID        INTEGER not null
        constraint Campagna_pk
            primary key,
    Nome        TEXT    not null,
    AziendaUUID INTEGER not null
        constraint Campagna_Azienda_fk
            references Azienda,
    unique (AziendaUUID, Nome)
);

create table Campo
(
    UUID         INTEGER not null
        constraint Campo_pk
            primary key,
    Nome         TEXT    not null,
    Dimensione   DOUBLE  not null,
    CampagnaUUID INTEGER not null
        constraint Campo_Campagna_UUID_fk
            references Campagna,
    unique (CampagnaUUID, Nome)
);

create table Attuatore
(
    UUID      INTEGER not null
        constraint Attuatore_pk
            primary key,
    Nome      TEXT,
    CampoUUID INTEGER not null
        constraint Attuatore_Campo_fk
            references Campo,
    unique (CampoUUID, Nome)
);

create table AttivazioniAttuatore
(
    UUID          INTEGER not null
        constraint AttivazioniAttuatore_pk
            primary key,
    AttuatoreUUID INTEGER not null
        constraint AttivazioniAttuatore_Attuatore_UUID_fk
            references Attuatore,
    Stato         BOOLEAN not null,
    Data          TEXT    not null
);

create table Coltivazione
(
    UUID                 INTEGER not null
        constraint Coltivazione_pk
            primary key,
    Raccolto             TEXT    not null,
    EsigenzaIdrica       TEXT,
    TipologiaIrrigazione TEXT,
    TemperaturaIdeale    DOUBLE,
    UmiditaIdeale        DOUBLE,
    DataSemina           TEXT    not null,
    CampoUUID            INTEGER not null
        constraint Coltivazione_Campo_UUID_fk
            references Campo,
    unique (CampoUUID, DataSemina)
);

create table RichiestaIdrica
(
    UUID             INTEGER not null
        constraint RichiestaIdrica_pk
            primary key,
    BacinoIdricoUUID INTEGER not null
        constraint RichiestaIdrica_BacinoIdrico__fk
            references BacinoIdrico,
    ColtivazioneUUID INTEGER not null
        constraint RichiestaIdrica_Coltivazione__fk
            references Coltivazione,
    Quantita         DOUBLE  not null,
    Data             TEXT    not null
);

create table RisorseAzienda
(
    UUID                     INTEGER not null
        constraint RisorseAzienda_pk
            primary key,
    AziendaUUID              INTEGER not null
        constraint RisorseAzienda_Azienda_fk
            references Azienda,
    Data                     TEXT    not null,
    DisponibilitaGiornaliera DOUBLE,
    ConsumoGiornaliero       DOUBLE
);

create table RisorseBacinoIdrico
(
    UUID                     INTEGER not null
        constraint RisorseBacinoIdrico_pk
            primary key,
    BacinoIdricoUUID         INTEGER not null
        constraint RisorseBacinoIdrico_Bacino_fk
            references BacinoIdrico,
    Data                     TEXT    not null,
    DisponibilitaGiornaliera DOUBLE,
    ConsumoGiornaliero       DOUBLE
);

create table Sensore
(
    UUID      INTEGER not null
        constraint Sensore_pk
            primary key,
    Nome      TEXT    not null,
    Tipologia TEXT    not null,
    CampoUUID INTEGER not null
        constraint Sensore_Campo_UUID_fk
            references Campo,
    unique (CampoUUID, Nome)
);

create table Misura
(
    UUID        INTEGER not null
        constraint Misura_pk
            primary key,
    Valore      DOUBLE  not null,
    Data        TEXT    not null,
    SensoreUUID INTEGER not null
        constraint Misura_Sensore_UUID_fk
            references Sensore
);

create table Utente
(
    UUID     INTEGER not null
        constraint Utente_pk
            primary key,
    Nome     TEXT    not null,
    Cognome  TEXT    not null,
    Mail     TEXT    not null,
    Password TEXT,
    Role     TEXT    not null,
    unique (Mail, Password)
);

create table GestoreAzienda
(
    UtenteUUID  INTEGER not null
        constraint GestoreAzienda_Utente_UUID_fk
            references Utente,
    AziendaUUID INTEGER not null
        constraint GestoreAzienda_Azienda_UUID_fk
            references Azienda,
    constraint GestoreAzienda_pk
        primary key (UtenteUUID, AziendaUUID),
    unique (UtenteUUID, AziendaUUID)
);

create table GestoreIdrico
(
    UtenteUUID       INTEGER not null
        constraint GestoreIdrico_Utente_UUID_fk
            references Utente,
    BacinoIdricoUUID INTEGER not null
        constraint GestoreIdrico_BacinoIdrico_UUID_fk
            references BacinoIdrico,
    constraint GestoreIdrico_pk
        primary key (UtenteUUID, BacinoIdricoUUID),
    unique (UtenteUUID, BacinoIdricoUUID)
);

create table ApprovazioneRichiestaIdrica
(
    UUID                INTEGER not null
        constraint ApprovazioneRichiestaIdrica_pk
            primary key,
    GestoreIdricoUUID   INTEGER not null
        constraint ApprovazioneRichiestaIdrica_GestoreIdrico__fk
            references GestoreIdrico (UtenteUUID),
    Approvazione        BOOLEAN not null,
    DataApprovazione    TEXT    not null,
    RichiestaIdricaUUID integer
        constraint ApprovazioneRichiestaIdrica_RichiestaIdrica_UUID_fk
            references RichiestaIdrica
);
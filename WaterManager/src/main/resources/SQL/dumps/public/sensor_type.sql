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


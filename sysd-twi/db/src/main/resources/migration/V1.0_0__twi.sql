create table twi
(
    id       bigserial primary key,
    tid      varchar(48)              not null,
    uid      varchar(16)              not null,
    content  varchar(256)             not null,
    date_time timestamp with time zone not null
);
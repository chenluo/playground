create table large_tbl
(
    id        serial primary key,
    is_target int          not null,
    uuid_col  varchar(256) not null
);
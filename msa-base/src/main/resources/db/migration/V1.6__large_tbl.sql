create table large_tbl
(
    id        bigint auto_increment primary key,
    is_target int          not null,
    json_col  json         not null,
    uuid_col  varchar(256) not null
);
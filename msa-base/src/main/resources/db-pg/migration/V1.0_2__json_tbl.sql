create table json_tbl
(
    id        serial primary key,
    json_col  jsonb
);
insert into json_tbl (json_col)
values (null);
update json_tbl set json_col = '{}' where id = 1;
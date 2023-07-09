create table reminder
(
    id               bigint auto_increment primary key,
    outbox_msg_id    bigint      not null,
    supplier_corp_id varchar(10) not null,
    type             int         not null,
    remind_time      datetime    not null,
    creator          varchar(10) not null,
    is_target        int         not null,
    version          varchar(10),
    sort_col         varchar(10)
)
create table message
(
    id              int auto_increment primary key,
    message_no      varchar(256) not null,
    message_type    varchar(256) not null,
    client_id       int          not null,
    state           int          not null,
    data_process_no int          not null,
    request_no      int          not null,
    detail          json         not null,
    sort_date_time  datetime     not null,
    constraint message_pk
        unique (message_type, client_id, message_no, state, request_no, data_process_no)
);
alter table consumed_message
    add constraint consumed_message_uk
        unique (uuid);
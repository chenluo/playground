create table follow_relation
(
    id       bigserial primary key,
    followee_uid      varchar(16)              not null,
    follower_uid      varchar(16)              not null
);
INSERT INTO twi.follow_relation (id, followee_uid, follower_uid) VALUES (1, '01', '02');
INSERT INTO twi.follow_relation (id, followee_uid, follower_uid) VALUES (2, '01', '03');
INSERT INTO twi.follow_relation (id, followee_uid, follower_uid) VALUES (3, '01', '04');
INSERT INTO twi.follow_relation (id, followee_uid, follower_uid) VALUES (4, '02', '03');
INSERT INTO twi.follow_relation (id, followee_uid, follower_uid) VALUES (5, '02', '04');
INSERT INTO twi.follow_relation (id, followee_uid, follower_uid) VALUES (6, '03', '04');
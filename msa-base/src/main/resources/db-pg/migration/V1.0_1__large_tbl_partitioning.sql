create table large_tbl_hash_partitioning
(
    id        serial primary key,
    is_target int          not null,
    uuid_col  varchar(256) not null
)
    partition by hash (id);
CREATE TABLE large_tbl_hash_partitioning_0 PARTITION OF large_tbl_hash_partitioning FOR VALUES WITH (MODULUS 4,REMAINDER 0);
CREATE TABLE large_tbl_hash_partitioning_1 PARTITION OF large_tbl_hash_partitioning FOR VALUES WITH (MODULUS 4,REMAINDER 1);
CREATE TABLE large_tbl_hash_partitioning_2 PARTITION OF large_tbl_hash_partitioning FOR VALUES WITH (MODULUS 4,REMAINDER 2);
CREATE TABLE large_tbl_hash_partitioning_3 PARTITION OF large_tbl_hash_partitioning FOR VALUES WITH (MODULUS 4,REMAINDER 3);
-- # CREATE TABLE large_tbl_hash_partitioning_0 PARTITION OF large_tbl_hash_partitioning FOR VALUES WITH (MODULUS 16,REMAINDER 0);
-- # CREATE TABLE large_tbl_hash_partitioning_1 PARTITION OF large_tbl_hash_partitioning FOR VALUES WITH (MODULUS 16,REMAINDER 1);
-- # CREATE TABLE large_tbl_hash_partitioning_2 PARTITION OF large_tbl_hash_partitioning FOR VALUES WITH (MODULUS 16,REMAINDER 2);
-- # CREATE TABLE large_tbl_hash_partitioning_3 PARTITION OF large_tbl_hash_partitioning FOR VALUES WITH (MODULUS 16,REMAINDER 3);
-- # CREATE TABLE large_tbl_hash_partitioning_4 PARTITION OF large_tbl_hash_partitioning FOR VALUES WITH (MODULUS 16,REMAINDER 4);
-- # CREATE TABLE large_tbl_hash_partitioning_5 PARTITION OF large_tbl_hash_partitioning FOR VALUES WITH (MODULUS 16,REMAINDER 5);
-- # CREATE TABLE large_tbl_hash_partitioning_6 PARTITION OF large_tbl_hash_partitioning FOR VALUES WITH (MODULUS 16,REMAINDER 6);
-- # CREATE TABLE large_tbl_hash_partitioning_7 PARTITION OF large_tbl_hash_partitioning FOR VALUES WITH (MODULUS 16,REMAINDER 7);
-- # CREATE TABLE large_tbl_hash_partitioning_8 PARTITION OF large_tbl_hash_partitioning FOR VALUES WITH (MODULUS 16,REMAINDER 8);
-- # CREATE TABLE large_tbl_hash_partitioning_9 PARTITION OF large_tbl_hash_partitioning FOR VALUES WITH (MODULUS 16,REMAINDER 9);
-- # CREATE TABLE large_tbl_hash_partitioning_10 PARTITION OF large_tbl_hash_partitioning FOR VALUES WITH (MODULUS 16,REMAINDER 10);
-- # CREATE TABLE large_tbl_hash_partitioning_11 PARTITION OF large_tbl_hash_partitioning FOR VALUES WITH (MODULUS 16,REMAINDER 11);
-- # CREATE TABLE large_tbl_hash_partitioning_12 PARTITION OF large_tbl_hash_partitioning FOR VALUES WITH (MODULUS 16,REMAINDER 12);
-- # CREATE TABLE large_tbl_hash_partitioning_13 PARTITION OF large_tbl_hash_partitioning FOR VALUES WITH (MODULUS 16,REMAINDER 13);
-- # CREATE TABLE large_tbl_hash_partitioning_14 PARTITION OF large_tbl_hash_partitioning FOR VALUES WITH (MODULUS 16,REMAINDER 14);
-- # CREATE TABLE large_tbl_hash_partitioning_15 PARTITION OF large_tbl_hash_partitioning FOR VALUES WITH (MODULUS 16,REMAINDER 15);

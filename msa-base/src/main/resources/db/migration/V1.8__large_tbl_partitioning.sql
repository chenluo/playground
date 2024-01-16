create table large_tbl_hash_partitioning
(
    id        bigint auto_increment primary key,
    is_target int          not null,
    uuid_col  varchar(256) not null
)
    partition by hash (`id`) partitions 16;

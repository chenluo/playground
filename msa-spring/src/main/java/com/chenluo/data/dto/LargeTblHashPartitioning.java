package com.chenluo.data.dto;

import org.springframework.data.relational.core.mapping.Table;

@Table("large_tbl_hash_partitioning")
public class LargeTblHashPartitioning extends LargeTbl {
    public LargeTblHashPartitioning() {
        super();
    }
}

package com.chenluo.data.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("varchar_tbl")
public class VarcharTbl {
    @Id
    public Integer id;
    public String str;

    public VarcharTbl(String str) {
        this.str = str;
    }
}

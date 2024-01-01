package com.chenluo.data.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("small_tbl")
public class SmallTbl {
    @Id private Integer id;
    private int isTarget;

    public SmallTbl() {}
}

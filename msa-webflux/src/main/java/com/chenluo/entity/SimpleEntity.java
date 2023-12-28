package com.chenluo.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("large_tbl")
public class SimpleEntity {
    @Id
    public int id;
    public int isTarget;

    public SimpleEntity(int id, int isTarget) {
        this.id = id;
        this.isTarget = isTarget;
    }
}

package com.chenluo.model.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("simple_tbl")
public class SimpleEntity {
    @Id
    public int id;
    public String uuid;
    public String val;

    public SimpleEntity(int id, String uuid, String val) {
        this.id = id;
        this.uuid = uuid;
        this.val = val;
    }

}
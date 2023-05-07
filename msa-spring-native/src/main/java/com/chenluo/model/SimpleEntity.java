package com.chenluo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("simple_tbl")
public class SimpleEntity {
    @Id
    public final Long id;
    public final String uuid;
    public final String val;

    public SimpleEntity(Long id, String uuid, String val) {
        this.id = id;
        this.uuid = uuid;
        this.val = val;
    }
}

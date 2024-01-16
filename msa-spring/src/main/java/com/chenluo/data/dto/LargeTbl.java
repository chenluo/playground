package com.chenluo.data.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("large_tbl")
public class LargeTbl {
    @Id private Long id;
    private int isTarget;
    private String uuidCol;

    public LargeTbl() {
        uuidCol = UUID.randomUUID().toString();
    }
}

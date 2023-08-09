package com.chenluo.data.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("large_tbl")
public class LargeTbl {
    @Id
    private Long id;
    private int isTarget;

    public LargeTbl() {
    }

}

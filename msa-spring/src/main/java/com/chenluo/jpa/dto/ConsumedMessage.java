package com.chenluo.jpa.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("consumed_message")
public class ConsumedMessage {
    @Id
    private int id;
    private String uuid;

    public ConsumedMessage(int id, String uuid) {
        this.id = id;
        this.uuid = uuid;
    }

}
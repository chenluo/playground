package com.chenluo.data.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("consumed_message")
public class ConsumedMessage {
    @Id public int id;
    public String uuid;

    public int count;
    public int success;

    public ConsumedMessage(int id, String uuid, int count, int success) {
        this.id = id;
        this.uuid = uuid;
        this.count = count;
        this.success = success;
    }
}

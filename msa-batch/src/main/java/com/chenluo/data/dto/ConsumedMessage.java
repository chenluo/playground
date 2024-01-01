package com.chenluo.data.dto;

import jakarta.persistence.*;

@Entity
@Table(name = "consumed_message")
public class ConsumedMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;

    public String uuid;

    public int count;
    public int success;

    public ConsumedMessage(int id, String uuid, int count, int success) {
        this.id = id;
        this.uuid = uuid;
        this.count = count;
        this.success = success;
    }

    public ConsumedMessage() {
    }
}

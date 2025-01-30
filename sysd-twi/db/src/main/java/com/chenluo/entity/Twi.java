package com.chenluo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.ZonedDateTime;

@Entity
public class Twi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id = null;
    public String tid;
    public String uid;
    public String content;
    public ZonedDateTime dateTime;

    public Twi(String tid, String uid, String content, ZonedDateTime dateTime) {
        this.tid = tid;
        this.uid = uid;
        this.content = content;
        this.dateTime = dateTime;
    }

    public Twi() {

    }
}

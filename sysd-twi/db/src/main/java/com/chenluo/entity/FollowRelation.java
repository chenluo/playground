package com.chenluo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class FollowRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id = null;
    public String followeeUid;
    public String followerUid;
}

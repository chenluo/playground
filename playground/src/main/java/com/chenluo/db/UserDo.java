package com.chenluo.db;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("account_tbl")
public class UserDo {
    @Id public int uid;

    @Column("user_id")
    public String userId;

    @Column("password")
    public String password;
}

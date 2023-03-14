package com.chenluo.jpa.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.ZonedDateTime;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int pk1;

    public String col1;
    public String col2;
    public ZonedDateTime col3;

    public Account(String col1, String col2, ZonedDateTime col3) {
        this.col1 = col1;
        this.col2 = col2;
        this.col3 = col3;
    }

    public Account() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        if (pk1 != account.pk1) return false;
        if (col1 != null ? !col1.equals(account.col1) : account.col1 != null) return false;
        if (col2 != null ? !col2.equals(account.col2) : account.col2 != null) return false;
        return col3 != null ? col3.equals(account.col3) : account.col3 == null;
    }

    @Override
    public int hashCode() {
        int result = pk1;
        result = 31 * result + (col1 != null ? col1.hashCode() : 0);
        result = 31 * result + (col2 != null ? col2.hashCode() : 0);
        result = 31 * result + (col3 != null ? col3.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Account{" +
                "pk1=" + pk1 +
                ", col1='" + col1 + '\'' +
                ", col2='" + col2 + '\'' +
                ", col3=" + col3 +
                '}';
    }
}

package com.chenluo.data.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("reminder")
public class Reminder {
    @Id private Long id;
    private int outboxMsgId;
    private String supplierCorpId;
    private int type;
    private LocalDateTime remindTime;
    private String creator;
    private int isTarget;
    private String version;
    private String sortCol;

    public Reminder() {}

    public Reminder(
            Long id,
            int outboxMsgId,
            String supplierCorpId,
            int type,
            LocalDateTime remindTime,
            String creator,
            int isTarget,
            String version,
            String sortCol) {
        this.id = id;
        this.outboxMsgId = outboxMsgId;
        this.supplierCorpId = supplierCorpId;
        this.type = type;
        this.remindTime = remindTime;
        this.creator = creator;
        this.isTarget = isTarget;
        this.version = version;
        this.sortCol = sortCol;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getOutboxMsgId() {
        return outboxMsgId;
    }

    public void setOutboxMsgId(int outboxMsgId) {
        this.outboxMsgId = outboxMsgId;
    }

    public String getSupplierCorpId() {
        return supplierCorpId;
    }

    public void setSupplierCorpId(String supplierCorpId) {
        this.supplierCorpId = supplierCorpId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public LocalDateTime getRemindTime() {
        return remindTime;
    }

    public void setRemindTime(LocalDateTime remindTime) {
        this.remindTime = remindTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public int getIsTarget() {
        return isTarget;
    }

    public void setIsTarget(int isTarget) {
        this.isTarget = isTarget;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSortCol() {
        return sortCol;
    }

    public void setSortCol(String sortCol) {
        this.sortCol = sortCol;
    }
}

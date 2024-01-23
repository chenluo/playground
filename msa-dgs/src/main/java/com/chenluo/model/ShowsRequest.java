package com.chenluo.model;

import java.math.BigDecimal;
import java.time.Instant;

public class ShowsRequest {
    public String id;
    public BigDecimal amount;

    public DisputeType disputeType;
    public Instant time;

    public ShowsRequest(String id, BigDecimal amount, DisputeType disputeType, Instant time) {
        this.id = id;
        this.amount = amount;
        this.disputeType = disputeType;
        this.time = time;
    }

    public ShowsRequest() {}
}

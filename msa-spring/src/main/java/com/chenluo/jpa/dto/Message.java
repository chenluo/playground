package com.chenluo.jpa.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("message")
public record Message(@Id Integer id, String messageNo, String messageType, Integer clientId,
                      Integer state, Integer dataProcessNo, Integer requestNo, String detail,
                      LocalDateTime sortDateTime) {
}

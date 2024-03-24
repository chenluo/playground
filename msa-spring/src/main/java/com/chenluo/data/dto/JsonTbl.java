package com.chenluo.data.dto;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("json_tbl")
public class JsonTbl {
    @Id private Long id;
    JsonNode json_col;
}

package com.chenluo.model.mapper;

import com.chenluo.model.dto.SimpleEntity;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimpleMapper extends CrudRepository<SimpleEntity, Integer> {

    @Query("SELECT * FROM simple_tbl WHERE uuid = :uuid")
    SimpleEntity findByUuid(String uuid);
}

package com.chenluo.jpa.repo;

import com.chenluo.jpa.dto.ConsumedMessage;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsumedMessageRepository extends CrudRepository<ConsumedMessage, Integer> {

    @Query("SELECT * FROM consumed_message WHERE uuid = :uuid")
    ConsumedMessage findByUuid(byte[] uuid);
}
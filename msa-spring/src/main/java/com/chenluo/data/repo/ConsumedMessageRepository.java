package com.chenluo.data.repo;

import com.chenluo.data.dto.ConsumedMessage;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsumedMessageRepository extends CrudRepository<ConsumedMessage, Integer> {

    @Query("SELECT * FROM consumed_message WHERE uuid = :uuid")
    ConsumedMessage findByUuid(String uuid);

    @Query("SELECT * FROM consumed_message WHERE uuid = :uuid for update")
    ConsumedMessage selectForUpdate(String uuid);
}
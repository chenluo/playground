package com.chenluo.data.repo;

import com.chenluo.data.dto.ConsumedMessage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsumedMessageRepository extends CrudRepository<ConsumedMessage, Integer> {

    ConsumedMessage findByUuid(String uuid);
}
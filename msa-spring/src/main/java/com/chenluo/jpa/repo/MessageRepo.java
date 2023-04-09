package com.chenluo.jpa.repo;

import com.chenluo.jpa.dto.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepo extends CrudRepository<Message, Integer> {
}

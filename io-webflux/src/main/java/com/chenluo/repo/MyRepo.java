package com.chenluo.repo;

import com.chenluo.entity.SimpleEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyRepo extends ReactiveCrudRepository<SimpleEntity, Integer> {
}

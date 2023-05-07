package com.chenluo.model.mapper;

import com.chenluo.model.SimpleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface SimpleEntityMapper extends CrudRepository<SimpleEntity, Long> {
}

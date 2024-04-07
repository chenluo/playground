package com.chenluo.data.repo;

import com.chenluo.data.dto.JsonTbl;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JsonTblRepository extends CrudRepository<JsonTbl, Long> {}

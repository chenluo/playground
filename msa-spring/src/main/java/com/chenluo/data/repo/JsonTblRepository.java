package com.chenluo.data.repo;

import com.chenluo.data.dto.JsonTbl;
import com.chenluo.data.dto.LargeTbl;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JsonTblRepository extends CrudRepository<JsonTbl, Long> {}

package com.chenluo.data.repo;

import com.chenluo.data.dto.SmallTbl;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SmallTblRepository extends CrudRepository<SmallTbl, Integer> {
}

package com.chenluo.data.repo;

import com.chenluo.data.dto.VarcharTbl;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VarcharTblRepository extends CrudRepository<VarcharTbl, Integer> {
}
package com.chenluo.data.repo;

import com.chenluo.data.dto.LargeTblHashPartitioning;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LargeTblHashPartitioningRepository extends
                                                    CrudRepository<LargeTblHashPartitioning, Long> {
}

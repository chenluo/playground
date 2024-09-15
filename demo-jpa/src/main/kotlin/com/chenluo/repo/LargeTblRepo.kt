package com.chenluo.repo

import com.chenluo.controller.DemoEntity
import jakarta.persistence.Cacheable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
@Cacheable(false)
interface LargeTblRepo : JpaRepository<LargeTblEntity, Long> {}

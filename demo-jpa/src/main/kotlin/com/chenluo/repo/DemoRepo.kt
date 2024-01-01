package com.chenluo.repo

import com.chenluo.controller.DemoEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface DemoRepo : JpaRepository<DemoEntity, Long>,
    JpaSpecificationExecutor<DemoEntity> {

}
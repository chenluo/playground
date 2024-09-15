package com.chenluo.controller

import com.chenluo.repo.DemoRepo
import com.chenluo.repo.LargeTblRepo
import jakarta.persistence.Entity
import jakarta.persistence.EntityManager
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root
import org.springframework.data.jpa.domain.Specification
import org.springframework.transaction.support.AbstractPlatformTransactionManager
import org.springframework.web.bind.annotation.*
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities.Private

@RestController
class DemoController(
    private val demoRepo: DemoRepo,
    private val largeTblRepo: LargeTblRepo,
    private val entityManager: EntityManager,
) {
    @GetMapping("/search")
    fun search(@RequestBody condition: SearchCondition): List<DemoEntity> {
        return demoRepo.findAll(ConditionPredicate(condition))
    }

    @GetMapping("/find")
    fun find(@RequestParam id: Long): DemoEntity {
        entityManager.clear()
        largeTblRepo.findById(1L)
        entityManager.refresh(largeTblRepo.findById(1L))
        return demoRepo.findById(id).orElseThrow()
    }

    @PostMapping("/update")
    fun update(@RequestParam id: Long): DemoEntity {
        val demoEntity = demoRepo.findById(id).orElseThrow()
        demoEntity.field1 += "1"
        return demoRepo.save(demoEntity)
    }
}

class ConditionPredicate(private val condition: SearchCondition) : Specification<DemoEntity> {
    override fun toPredicate(
        root: Root<DemoEntity>,
        query: CriteriaQuery<*>,
        cb: CriteriaBuilder
    ): Predicate? {
        var p: Predicate? = null
        if (condition.field1 != null) p = cb.equal(root.get<String>("field1"), condition.field1)
        if (condition.jsonbField1 != null) {
            val p2 =
                cb.equal(
                    cb.function(
                        "jsonb_extract_path_text",
                        String::class.java,
                        root.get<String>("jsonbField"),
                        cb.literal("field1")
                    ),
                    condition.jsonbField1
                )
            if (p == null) {
                p = p2
            } else {
                p = cb.and(p, p2)
            }
        }
        return p
    }
}

data class SearchCondition(val field1: String?, val jsonbField1: String?) {}

@Entity
data class DemoEntity(
    @Id @GeneratedValue var id: Long? = null,
    var field1: String,
    @jakarta.persistence.Version var version: Int
) {}

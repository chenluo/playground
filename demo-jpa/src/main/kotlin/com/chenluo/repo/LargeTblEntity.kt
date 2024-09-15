package com.chenluo.repo

import jakarta.persistence.*

@Entity
@Table(name = "large_tbl")
@Cacheable(false)
data class LargeTblEntity(
    @Id @GeneratedValue val id: Long? = null,
    var isTarget: Int,
    var uuidCol: String
) {

}

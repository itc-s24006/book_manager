/*
 * Auto-generated file. Created by MyBatis Generator
 */
package jp.ac.it_college.std.s24006.kotlin.book.manager.infrastructure.database.record

import jp.ac.it_college.std.s24006.kotlin.book.manager.domain.types.RoleType

data class User(
    var id: Long? = null,
    var email: String? = null,
    var password: String? = null,
    var name: String? = null,
    var roleType: RoleType? = null
)
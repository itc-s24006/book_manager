/*
 * Auto-generated file. Created by MyBatis Generator
 */
package jp.ac.it_college.std.s24006.kotlin.book.manager.infrastructure.database.record
//recordディレクトリ  DBテーブルのレコード定義

import java.time.LocalDate

data class Book(
    var id: Long? = null,
    var title: String? = null,
    var author: String? = null,
    var releaseDate: LocalDate? = null
)
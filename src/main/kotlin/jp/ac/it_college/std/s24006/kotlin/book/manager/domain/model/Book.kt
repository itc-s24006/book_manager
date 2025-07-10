package jp.ac.it_college.std.s24006.kotlin.book.manager.domain.model
//ドメインモデル(中心)となるデータクラス

import kotlinx.datetime.LocalDate

data class Book(
    val id: Long,
    val title: String,
    val author: String,
    val releaseDate: LocalDate
)

package jp.ac.it_college.std.s24006.kotlin.book.manager.domain.model

import kotlinx.datetime.LocalDateTime

data class Rental(
    val bookId: Long,
    val userId: Long,
    val rentalDatetime: LocalDateTime,
    val returnDeadline: LocalDateTime,
)

package jp.ac.it_college.std.s24006.kotlin.book.manager.domain.repository

import jp.ac.it_college.std.s24006.kotlin.book.manager.domain.model.Rental

interface RentalRepository {
    fun startRental(rental: Rental)

    fun endRental(bookId: Long)
}
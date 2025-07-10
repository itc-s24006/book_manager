package jp.ac.it_college.std.s24006.kotlin.book.manager.domain.repository

import jp.ac.it_college.std.s24006.kotlin.book.manager.domain.model.BookWithRental

interface BookRepository {
    fun findAllWithRental(): List<BookWithRental>
}


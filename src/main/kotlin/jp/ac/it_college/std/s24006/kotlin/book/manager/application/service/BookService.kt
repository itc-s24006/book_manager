package jp.ac.it_college.std.s24006.kotlin.book.manager.application.service

import jp.ac.it_college.std.s24006.kotlin.book.manager.domain.model.BookWithRental
import jp.ac.it_college.std.s24006.kotlin.book.manager.domain.repository.BookRepository
import org.springframework.stereotype.Service

//DIで注入対象とするための記述
@Service
class BookService(
    private val bookRepository: BookRepository
) {
    fun getList(): List<BookWithRental> {
        return bookRepository.findAllWithRental()
    }
}
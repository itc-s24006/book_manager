package jp.ac.it_college.std.s24006.kotlin.book.manager.application.service
//利用者向けの本の検索や一覧取得などを行う

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
    fun getDetail(bookId: Long): BookWithRental {
        return bookRepository.findWithRental(bookId)
            ?: throw IllegalArgumentException("存在しない書籍ID: ${bookId}")
    }

}
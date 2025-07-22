package jp.ac.it_college.std.s24006.kotlin.book.manager.application.service
//管理者向けの本操作（登録・更新・削除など）を行う

import jp.ac.it_college.std.s24006.kotlin.book.manager.domain.model.Book
import jp.ac.it_college.std.s24006.kotlin.book.manager.domain.repository.BookRepository
import kotlinx.datetime.LocalDate
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.ResponseStatus

@Service
class AdminBookService(
    private val bookRepository: BookRepository) {
    @Transactional
    fun register(book: Book) {
        bookRepository.findWithRental(book.id)?.let{
            //すでに同じIDの書籍が存在したらエラーを返し、処理をなかったことにする(rollback)
            throw @ResponseStatus(HttpStatus.CONFLICT) object
                : IllegalArgumentException("すでに存在する書籍ID: ${book.id}"){}
        }
        bookRepository.register(book)
    }

    @Transactional
    fun update(bookId: Long, title: String?, author: String?, releaseDate: LocalDate?) {
        bookRepository.findWithRental(bookId)
            ?: throw @ResponseStatus(HttpStatus.NOT_FOUND) object
                : java.lang.IllegalArgumentException("存在しない書籍ID: ${bookId}") {}
        bookRepository.update(bookId, title, author, releaseDate)
    }

    @Transactional
    fun delete(bookId: Long) {
        bookRepository.findWithRental(bookId)
            ?: throw @ResponseStatus(HttpStatus.NOT_FOUND) object
                : java.lang.IllegalArgumentException("存在しない書籍ID: ${bookId}") {}
        bookRepository.delete(bookId)
    }
}
package jp.ac.it_college.std.s24006.kotlin.book.manager.application.service

import jp.ac.it_college.std.s24006.kotlin.book.manager.domain.model.Rental
import jp.ac.it_college.std.s24006.kotlin.book.manager.domain.repository.BookRepository
import jp.ac.it_college.std.s24006.kotlin.book.manager.domain.repository.RentalRepository
import jp.ac.it_college.std.s24006.kotlin.book.manager.domain.repository.UserRepository
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.ResponseStatus
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days
import kotlin.time.ExperimentalTime

private const val RENTAL_TERM_DAYS = 14L //貸出期限
private val JST = TimeZone.of("Asia/Tokyo")

@Service
class RentalService(
    private val userRepository: UserRepository, //ユーザーが実在してるかどうか確認するため
    private val bookRepository: BookRepository, //指定した書籍があるかどうか
    private val rentalRepository: RentalRepository
) {
    @OptIn(ExperimentalTime::class)
    @Transactional
    fun startRental(bookId: Long, userId: Long) {
        userRepository.find(userId) ?: throw @ResponseStatus(HttpStatus.FORBIDDEN) object
            : IllegalArgumentException("該当するユーザーが存在しません ID: $userId") {}
        val book = bookRepository.findWithRental(bookId)
            ?: throw @ResponseStatus(HttpStatus.NOT_FOUND) object
                : IllegalArgumentException("該当する書籍が存在しません ID: $bookId") {}
        if (book.isRental) throw @ResponseStatus(HttpStatus.CONFLICT) object
            : IllegalArgumentException("貸出中の書籍です ID: $bookId") {}

        val now = Clock.System.now()
        val rentalDatetime = now.toLocalDateTime(JST)
        val returnDeadline = now.plus(RENTAL_TERM_DAYS.days).toLocalDateTime(JST)

        rentalRepository.startRental(
            Rental(bookId, userId, rentalDatetime, returnDeadline)
        )
    }
}
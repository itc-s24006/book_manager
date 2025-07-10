package jp.ac.it_college.std.s24006.kotlin.book.manager.infrastructure.repository

import jp.ac.it_college.std.s24006.kotlin.book.manager.domain.model.Book
import jp.ac.it_college.std.s24006.kotlin.book.manager.domain.model.BookWithRental
import jp.ac.it_college.std.s24006.kotlin.book.manager.domain.model.Rental
import jp.ac.it_college.std.s24006.kotlin.book.manager.domain.repository.BookRepository
import jp.ac.it_college.std.s24006.kotlin.book.manager.infrastructure.database.mapper.BookWithRentalMapper
import jp.ac.it_college.std.s24006.kotlin.book.manager.infrastructure.database.mapper.select
import kotlinx.datetime.toKotlinLocalDate
import kotlinx.datetime.toKotlinLocalDateTime
import org.springframework.stereotype.Repository
import jp.ac.it_college.std.s24006.kotlin.book.manager.infrastructure.database.record.BookWithRental as BookWithRentalRecord

@Repository
class BookRepositoryImpl (
    private val bookWithRentalMapper: BookWithRentalMapper
): BookRepository {
    override fun findAllWithRental(): List<BookWithRental> {
        return bookWithRentalMapper.select().map(::toModel)
    }

    //MybatisのBookWithRentalと、domainのBookWithRentalを対応させる
    private fun toModel(record: BookWithRentalRecord) = record.run {
        BookWithRental(
            Book( // !! 全部強制的に、非null許容型に変換
                id!!,
                title!!,
                author!!,
                releaseDate!!.toKotlinLocalDate()

            ),
            userId?.let { //.? セーフコール　nullじゃないときだけ通る
                Rental(
                    id!!,
                    userId!!,
                    rentalDatetime!!.toKotlinLocalDateTime(),
                    returnDeadline!!.toKotlinLocalDateTime()
                )
            }
        )
    }

}

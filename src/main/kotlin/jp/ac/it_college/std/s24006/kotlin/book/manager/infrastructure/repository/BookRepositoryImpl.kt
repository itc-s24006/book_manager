package jp.ac.it_college.std.s24006.kotlin.book.manager.infrastructure.repository

import jp.ac.it_college.std.s24006.kotlin.book.manager.domain.model.Book
import jp.ac.it_college.std.s24006.kotlin.book.manager.domain.model.BookWithRental
import jp.ac.it_college.std.s24006.kotlin.book.manager.domain.model.Rental
import jp.ac.it_college.std.s24006.kotlin.book.manager.domain.repository.BookRepository
import jp.ac.it_college.std.s24006.kotlin.book.manager.infrastructure.database.mapper.BookMapper
import jp.ac.it_college.std.s24006.kotlin.book.manager.infrastructure.database.mapper.BookWithRentalMapper
import jp.ac.it_college.std.s24006.kotlin.book.manager.infrastructure.database.mapper.deleteByPrimaryKey
import jp.ac.it_college.std.s24006.kotlin.book.manager.infrastructure.database.mapper.findByPrimaryKey
import jp.ac.it_college.std.s24006.kotlin.book.manager.infrastructure.database.mapper.insert
import jp.ac.it_college.std.s24006.kotlin.book.manager.infrastructure.database.mapper.select
import jp.ac.it_college.std.s24006.kotlin.book.manager.infrastructure.database.mapper.updateByPrimaryKeySelective
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toKotlinLocalDate
import kotlinx.datetime.toKotlinLocalDateTime
import org.springframework.stereotype.Repository
import jp.ac.it_college.std.s24006.kotlin.book.manager.infrastructure.database.record.BookWithRental as BookWithRentalRecord
import jp.ac.it_college.std.s24006.kotlin.book.manager.infrastructure.database.record.Book as BookRecord

@Repository
class BookRepositoryImpl(
    private val bookWithRentalMapper: BookWithRentalMapper,
    private val bookMapper: BookMapper
): BookRepository {
    override fun findAllWithRental(): List<BookWithRental> {
        return bookWithRentalMapper.select().map(::toModel)
    }

    override fun findWithRental(bookId: Long): BookWithRental? {
        return bookWithRentalMapper.findByPrimaryKey(bookId)?.let(::toModel)
    }

    override fun register(book: Book) {
        bookMapper.insert(toRecord(book))
    }

    override fun update(
        id: Long,
        title: String?,
        author: String?,
        releaseDate: LocalDate?
    ) {
        bookMapper.updateByPrimaryKeySelective(
            BookRecord(id, title, author, releaseDate?.toJavaLocalDate())
        )
    }

    override fun delete(id: Long) {
        bookMapper.deleteByPrimaryKey(id)
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

    private fun toRecord(model: Book): BookRecord = model.run {
        BookRecord(
            id,
            title,
            author,
            releaseDate.toJavaLocalDate()
        )
    }
}

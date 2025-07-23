package jp.ac.it_college.std.s24006.kotlin.book.manager.infrastructure.repository

import jp.ac.it_college.std.s24006.kotlin.book.manager.domain.model.Rental
import jp.ac.it_college.std.s24006.kotlin.book.manager.domain.repository.RentalRepository
import jp.ac.it_college.std.s24006.kotlin.book.manager.infrastructure.database.mapper.RentalDynamicSqlSupport
import jp.ac.it_college.std.s24006.kotlin.book.manager.infrastructure.database.mapper.RentalMapper
import jp.ac.it_college.std.s24006.kotlin.book.manager.infrastructure.database.mapper.delete
import jp.ac.it_college.std.s24006.kotlin.book.manager.infrastructure.database.mapper.insert
import kotlinx.datetime.toJavaLocalDateTime
import org.springframework.stereotype.Repository
import jp.ac.it_college.std.s24006.kotlin.book.manager.infrastructure.database.record.Rental as RentalRecord

@Repository
class RentalRepositoryImpl(
    private val rentalMapper: RentalMapper
) : RentalRepository {
    override fun startRental(rental: Rental) {
        rentalMapper.insert(toRecord(rental))
    }

    override fun endRental(bookId: Long) {
        //教科書と違う
        rentalMapper.delete {
            where {
                RentalDynamicSqlSupport.bookId isEqualTo bookId
            }
        }
    }

    private fun toRecord(model: Rental): RentalRecord = model.run {
        RentalRecord(
            bookId,
            userId,
            rentalDatetime.toJavaLocalDateTime(),
            returnDeadline.toJavaLocalDateTime()
        )
    }
}
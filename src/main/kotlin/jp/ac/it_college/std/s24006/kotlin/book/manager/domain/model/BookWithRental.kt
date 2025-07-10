package jp.ac.it_college.std.s24006.kotlin.book.manager.domain.model

data class BookWithRental(
    val book: Book,
    val rental: Rental?,
) {
    //借りられていたらtrue
    val isRental: Boolean
        get() = rental != null
}


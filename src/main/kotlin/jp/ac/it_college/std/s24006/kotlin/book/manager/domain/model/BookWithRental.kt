package jp.ac.it_college.std.s24006.kotlin.book.manager.domain.model
//貸出状態の本を定義しているドメインモデル

data class BookWithRental(
    val book: Book,
    val rental: Rental?,
) {
    //借りられていたらtrue
    val isRental: Boolean
        get() = rental != null
}


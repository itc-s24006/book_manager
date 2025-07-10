package jp.ac.it_college.std.s24006.kotlin.book.manager.presentation.form

import jp.ac.it_college.std.s24006.kotlin.book.manager.domain.model.BookWithRental
import kotlinx.serialization.Serializable

@Serializable
data class GetBookListResponse(
    val bookList: List<BookInfo>
)

@Serializable
data class BookInfo(
    val id: Long,
    val title: String,
    val author: String,
    val isRental: Boolean
) {
    //↓セカンダリコンストラクタを定義しておくと、コントローラーの実装がシンプルになる
    constructor(model: BookWithRental): this(
        model.book.id,
        model.book.title,
        model.book.author,
        model.isRental
    )
}
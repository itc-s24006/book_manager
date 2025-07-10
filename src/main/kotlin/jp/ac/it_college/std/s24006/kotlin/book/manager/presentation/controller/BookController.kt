package jp.ac.it_college.std.s24006.kotlin.book.manager.presentation.controller

import jp.ac.it_college.std.s24006.kotlin.book.manager.application.service.BookService
import jp.ac.it_college.std.s24006.kotlin.book.manager.presentation.form.BookInfo
import jp.ac.it_college.std.s24006.kotlin.book.manager.presentation.form.GetBookListResponse
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/book")
@CrossOrigin
class BookController(
    private val bookService: BookService
) {
    @GetMapping("/list")
    fun getList(): GetBookListResponse {
        val bookList = bookService.getList().map(::BookInfo)
        return GetBookListResponse(bookList)
    }
}
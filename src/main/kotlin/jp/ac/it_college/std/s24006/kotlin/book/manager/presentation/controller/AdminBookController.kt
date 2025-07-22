package jp.ac.it_college.std.s24006.kotlin.book.manager.presentation.controller

import jp.ac.it_college.std.s24006.kotlin.book.manager.application.service.AdminBookService
import jp.ac.it_college.std.s24006.kotlin.book.manager.domain.model.Book
import jp.ac.it_college.std.s24006.kotlin.book.manager.presentation.form.RegisterBookRequest
import jp.ac.it_college.std.s24006.kotlin.book.manager.presentation.form.UpdateBookRequest
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/admin/book")
@CrossOrigin
class AdminBookController(
    private val adminBookService: AdminBookService
) {
    @PostMapping("/register")
    fun register(@RequestBody request: RegisterBookRequest){
        adminBookService.register(
            request.run {
                Book(id, title, author, releaseDate)
            }
        )
    }

    @PutMapping("/update")
    fun update(@RequestBody request: UpdateBookRequest) {
        request.run {
            adminBookService.update(
                id, title, author, releaseDate
            )
        }
    }

    @DeleteMapping("/delete/{bookId}")
    fun delete(@PathVariable bookId: Long) {
        adminBookService.delete(bookId)
    }
}
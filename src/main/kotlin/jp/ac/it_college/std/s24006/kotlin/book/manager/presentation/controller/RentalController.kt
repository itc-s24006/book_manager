package jp.ac.it_college.std.s24006.kotlin.book.manager.presentation.controller

import jp.ac.it_college.std.s24006.kotlin.book.manager.application.service.RentalService
import jp.ac.it_college.std.s24006.kotlin.book.manager.application.service.security.BookManagerUserDetails
import jp.ac.it_college.std.s24006.kotlin.book.manager.presentation.form.RentalStartRequest
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/rental")
@CrossOrigin
class RentalController(
    private val rentalService: RentalService
) {
    @PostMapping("/start")
    fun startRental(
        @RequestBody request: RentalStartRequest,
        @AuthenticationPrincipal user: BookManagerUserDetails
    ) {
        rentalService.startRental(request.bookId, user.id)
    }

    @DeleteMapping("/end/{bookId}")
    fun endRental(
        @PathVariable bookId: Long,
        @AuthenticationPrincipal user: BookManagerUserDetails
    ) {
        rentalService.endRental(bookId, user.id)
    }
}
package jp.ac.it_college.std.s24006.kotlin.book.manager.domain.repository

import jp.ac.it_college.std.s24006.kotlin.book.manager.domain.model.User

interface UserRepository {
    //ユーザーを検索する側
    //登録していないemailを入力される可能性もあるからnull許容型
    fun find(email: String): User?

    fun find(id:Long): User? //id検索

}
package jp.ac.it_college.std.s24006.kotlin.book.manager.infrastructure.repository

import jp.ac.it_college.std.s24006.kotlin.book.manager.domain.model.User
import jp.ac.it_college.std.s24006.kotlin.book.manager.domain.repository.UserRepository
import jp.ac.it_college.std.s24006.kotlin.book.manager.infrastructure.database.mapper.UserDynamicSqlSupport
import jp.ac.it_college.std.s24006.kotlin.book.manager.infrastructure.database.mapper.UserMapper
import jp.ac.it_college.std.s24006.kotlin.book.manager.infrastructure.database.mapper.selectByPrimaryKey
import jp.ac.it_college.std.s24006.kotlin.book.manager.infrastructure.database.mapper.selectOne
import org.springframework.stereotype.Repository
import jp.ac.it_college.std.s24006.kotlin.book.manager.infrastructure.database.record.User as UserRecord

@Repository //DIの対象
class UserRepositoryImpl(
    private val mapper: UserMapper
): UserRepository {
    //ユーザーを検索する側
    override fun find(email: String): User? {
        val record = mapper.selectOne {
            where {
                UserDynamicSqlSupport.email isEqualTo email
            }
        }
        return record?.let(::toModel)
    }

    override fun find(id: Long): User? {
        return mapper.selectByPrimaryKey(id)?.let(::toModel)
    }

    //ドメインモデルに変換する
    private fun toModel(record: UserRecord): User = record.run {
        User(id!!,
            email!!,
            password!!,
            name!!,
            roleType!!)
    }

}
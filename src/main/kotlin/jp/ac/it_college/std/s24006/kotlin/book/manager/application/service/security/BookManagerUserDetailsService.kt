package jp.ac.it_college.std.s24006.kotlin.book.manager.application.service.security
//Spring Security の UserDetailsService を実装
//ログイン時にユーザー情報をDBから取得して認証に使う

import jp.ac.it_college.std.s24006.kotlin.book.manager.application.service.AuthenticationService
import jp.ac.it_college.std.s24006.kotlin.book.manager.domain.model.User
import jp.ac.it_college.std.s24006.kotlin.book.manager.domain.types.RoleType
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException

class BookManagerUserDetailsService(
    private val authenticationService: AuthenticationService
) : UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails? {
        //Usernameが取得できなかった場合
        username ?: throw UsernameNotFoundException("メールアドレスかパスワードが違います")

        val user = authenticationService.findUser(username)
            ?: throw UsernameNotFoundException("メールアドレスかパスワードが違います")
        return BookManagerUserDetails(user)
    }
}

data class BookManagerUserDetails(
    val id: Long,
    val email: String,
    private val password: String,
    //↑Javaでコンパイルしたときにゲッターの名前 getPassword() が被ってしまうので、
    // private にする
    val roleType: RoleType
) : UserDetails { //UserDetailsはインターフェースだから実装する必要がある
    constructor(user: User) : this(user.id, user.email, user.password, user.roleType)
    //権限RoleTypeの情報を含むデータを返す
    override fun getAuthorities(): Collection<GrantedAuthority?>? {
        return AuthorityUtils.createAuthorityList(listOf(roleType.name))
    }
    override fun getPassword(): String? {
        return password
    }
    override fun getUsername(): String? {
        return email
    }
    //UserDetailsの上の３つ以外のメソッドはデフォルトの処理があるから３つだけオーバーライド

}

package jp.ac.it_college.std.s24006.kotlin.book.manager.presentation.config

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jp.ac.it_college.std.s24006.kotlin.book.manager.application.service.AuthenticationService
import jp.ac.it_college.std.s24006.kotlin.book.manager.application.service.security.BookManagerUserDetailsService
import jp.ac.it_college.std.s24006.kotlin.book.manager.domain.types.RoleType
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource


@Configuration // ↓何かの設定を変更するクラスだと示す
@EnableWebSecurity //HTTPに関するセキュリティーの設定
class WebSecurityConfig(
    private val authenticationService: AuthenticationService
) {
    @Bean //HTTP関連の基本的な設定用のメソッドと示す
    @Order(1) //優先順位は1番目
    fun configure(http: HttpSecurity): SecurityFilterChain {
        //http {} を Tab を使わずに打ち、
        //import org.springframework.security.config.annotation.web.invoke を手打ち
        http {
            //どこ宛のアクセスを許可するか = パスそれぞれの設定
            authorizeHttpRequests {
                // 誰でもアクセス可能
                authorize("/login", permitAll) //permitAll　無条件でアクセスできる
                // 管理者ユーザーのみアクセス可能
                //　/admin/** -> *一つだったら、admin以下の一つの階層までしか許可されないけど、２つあったら頭にadminがついているすべてのファイルを許可できる
                // hasAuthority -> ()内の権限を持っていればという意味　絶対""で直書きじゃなくて参照する
                authorize("/admin/**", hasAuthority(RoleType.ADMIN.name))
                // それ以外のパス -> anyRequest
                // ログインが必要 -> authenticated
                authorize(anyRequest, authenticated)
            }
            //ログイン機能の設定
            formLogin {
                loginProcessingUrl = "/login"//ログイン用のURLを指定
                usernameParameter = "email"  //email を username として扱うように変更
                passwordParameter = "pass"
                //httpのレスポンスコードを適切に返すという設定↓
                authenticationSuccessHandler = AuthenticationHandler //ログインに成功した場合
                authenticationFailureHandler = AuthenticationHandler //ログインに失敗した場合
            }
            //クロスサイトリクエストフォージェリという攻撃を対策する機能をあえてOFFにする
            //→ 　必ず正規のサイトにアクセスしないと実行できないようにする
            csrf {
                disable() //無効
            }
            //例外処理　権限がなかった場合の処理
            exceptionHandling {
                //↓restAPIのためフォームがないから、単にログインできないというカスタマイズ
                authenticationEntryPoint = AuthenticationHandler
                //単に403画面を表示させるカスタマイズ
                accessDeniedHandler = AuthenticationHandler
            }
        }
        return http.build() //設定を終え、適用するためのオブジェクトを構築・生成する

    }

    //Spring Securityに必要なユーザー認証設定
    @Bean
    fun userDetailsService(): UserDetailsService =
        BookManagerUserDetailsService(authenticationService)


    @Bean
    //パスワードをエンコードしたものを返す関数
    fun passwordEncoder(): PasswordEncoder =
        //Argon2で計算に必要なパラメータを設定
        Argon2PasswordEncoder(16, 32, 1, 19456, 2)


    @Bean
    /* フロントエンドとバックエンドが別々で作られていた場合、IPアドレスやドメイン名が異なることがある
       そのような状態を「オリジンが異なる」といい、デフォでは「同一オリジンポリシー」という仕様でブロックされてしまう */
    //異なるウェブサイト同士( 異なるオリジン同士 )でデータのやり取りをするための仕様 -> cors の設定
    /*プリフライトリクエストで正しいレスポンスを返すための設定
    　プリフライトリクエスト → 実際の通信をする前に、正しい通信先か判断するためにレスポンス内容を確認する
     */
    fun corsConfigurationSource(): UrlBasedCorsConfigurationSource {
        val config = CorsConfiguration().apply {
            //以下の上３つ＝根本の設定　ブラウザが正常なリクエストと判断できる
            allowedMethods = listOf(CorsConfiguration.ALL)
            allowedHeaders = listOf(CorsConfiguration.ALL) //どこからのアクセスを想定しているか
            allowedOrigins = listOf(
                "http://localhost:3000",
                "http://127.0.0.1:3000"
            )
            /*↓ 機密情報(cookie)を含めて送信することを許可している
                この記述がないとブラウザがブロックしてしまう*/
            allowCredentials = true
        }
        return UrlBasedCorsConfigurationSource().apply {
            //すべてのリクエストに対して上の設定を適用するという処理
            registerCorsConfiguration("/**", config)
        }
    }
}


object AuthenticationHandler :
    AuthenticationSuccessHandler, //認証が成功した時
    AuthenticationFailureHandler, //認証が失敗した時
    AuthenticationEntryPoint, //未認証なので認証して
    AccessDeniedHandler //アクセスが拒否された(権限が無い等)
{
    // AuthenticationSuccessHandler
    override fun onAuthenticationSuccess(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authentication: Authentication?
    ) {
        response?.status = HttpServletResponse.SC_OK //認証OKなら200を返す
    }

    // AuthenticationFailureHandler
    override fun onAuthenticationFailure(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        exception: AuthenticationException?
    ) {
        response?.status = HttpServletResponse.SC_UNAUTHORIZED//認証失敗なら401を返す
    }

    // AuthenticationEntryPoint
    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authException: AuthenticationException?
    ) {
        response?.status = HttpServletResponse.SC_UNAUTHORIZED //未認証でも401を返す
    }

    // AccessDeniedHandler
    override fun handle(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        accessDeniedException: AccessDeniedException?
    ) {
        response?.status = HttpServletResponse.SC_FORBIDDEN //権限がなかった時は403を返す
    }
}
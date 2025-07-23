package jp.ac.it_college.std.s24006.kotlin.book.manager.presentation.aop

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.After
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.AfterThrowing
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.slf4j.LoggerFactory
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder

//ログ専用のクラスが用意されている
private val logger = LoggerFactory.getLogger(LoggingAdvice::class.java)

@Aspect
@Component
class LoggingAdvice {
    @Before("execution(* jp.ac.it_college.std.s24006.kotlin.book.manager.presentation.controller..*.*(..))")
    //@Before 直前に    execution() 実行
    // controller..*.* controller配下にある ..*すべてのクラスの .*すべてのメソッドの (..)どんな戻り値でも対象という意味

    //ログを表示するフォーマット指定
    fun beforeLog(joinPoint: JoinPoint) {
        //教科書だと変換してるが、エラーになるから型変換せずそのまま使う
        val user = SecurityContextHolder.getContext().authentication.principal
        logger.info("Start: ${joinPoint.signature}")
        logger.info("Class: ${joinPoint.target.javaClass}")
        logger.info("Principal: $user")
        logger.info("Session: ${RequestContextHolder.getRequestAttributes()?.sessionId}")
    }


    @After("execution(* jp.ac.it_college.std.s24006.kotlin.book.manager.presentation.controller..*.*(..))")
    fun afterLog(joinPoint: JoinPoint) {
        val user = SecurityContextHolder.getContext().authentication.principal
        logger.info("End: ${joinPoint.signature}")
        logger.info("Principal: $user")
    }

    //戻り値②ぷじて実行する後処理
    @AfterReturning("execution(* jp.ac.it_college.std.s24006.kotlin.book.manager.presentation.controller..*.*(..))",
        returning = "returnValue")
    fun afterReturningLog(joinPoint: JoinPoint, returnValue: Any?) {
        logger.info("End: ${joinPoint.signature} returnValue: $returnValue")
    }

    //例外の種類に応じて実行する後処理
    @AfterThrowing("execution(* jp.ac.it_college.std.s24006.kotlin.book.manager.presentation.controller..*.*(..))",
        throwing = "e")
    fun afterThrowingLog(joinPoint: JoinPoint, e: Throwable) {
        logger.error("Exception: ${e.javaClass} signature=${joinPoint.signature} message=${e.message}")
/*      ログレベル
        　ログの INFO とかの表示。ひと目で重要度がわかる
        　開発時には詳細なログを表示させたいが、ユーザーに渡した後も同じようにログを出力するとメモリを逼迫するから
        　ログレベルを適切に設定していたら、「このレベル以下のログは表示しない」などの制御ができる*/
    }
}
package jp.ac.it_college.std.s24006.kotlin.book.manager.presentation.config

import ch.qos.logback.classic.pattern.MessageConverter
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.KotlinSerializationJsonHttpMessageConverter

//spring bootを設定するためのクラス
@Configuration
class KotlinSerialization {

    //自動で読み込まれる記述。Configurationだけでは自動読み込みされない
    @OptIn(ExperimentalSerializationApi::class)
    @Bean
    fun messageConverter(): KotlinSerializationJsonHttpMessageConverter {
        val json = Json {
            ignoreUnknownKeys = true //想定してないキーを持つJsonデータを受け入れたら無視するオプション
            explicitNulls = false //explicit=明示的な  明示的なnullは必要ないという意味(意味わからんけどデフォルト)
            namingStrategy = JsonNamingStrategy.SnakeCase
        }
        return KotlinSerializationJsonHttpMessageConverter(json)
    }
}
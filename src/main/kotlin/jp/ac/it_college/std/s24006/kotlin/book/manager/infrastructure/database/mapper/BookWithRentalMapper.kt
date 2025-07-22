package jp.ac.it_college.std.s24006.kotlin.book.manager.infrastructure.database.mapper
//欲しい機能があったらインターフェースを作成してそれをmybatisに投げると実装できる

import jp.ac.it_college.std.s24006.kotlin.book.manager.infrastructure.database.mapper.BookDynamicSqlSupport.author
import jp.ac.it_college.std.s24006.kotlin.book.manager.infrastructure.database.mapper.BookDynamicSqlSupport.book
import jp.ac.it_college.std.s24006.kotlin.book.manager.infrastructure.database.mapper.BookDynamicSqlSupport.id
import jp.ac.it_college.std.s24006.kotlin.book.manager.infrastructure.database.mapper.BookDynamicSqlSupport.releaseDate
import jp.ac.it_college.std.s24006.kotlin.book.manager.infrastructure.database.mapper.BookDynamicSqlSupport.title
import jp.ac.it_college.std.s24006.kotlin.book.manager.infrastructure.database.mapper.RentalDynamicSqlSupport.rental
import jp.ac.it_college.std.s24006.kotlin.book.manager.infrastructure.database.mapper.RentalDynamicSqlSupport.rentalDatetime
import jp.ac.it_college.std.s24006.kotlin.book.manager.infrastructure.database.mapper.RentalDynamicSqlSupport.returnDeadline
import jp.ac.it_college.std.s24006.kotlin.book.manager.infrastructure.database.mapper.RentalDynamicSqlSupport.userId
import jp.ac.it_college.std.s24006.kotlin.book.manager.infrastructure.database.record.BookWithRental
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Result
import org.apache.ibatis.annotations.ResultMap
import org.apache.ibatis.annotations.Results
import org.apache.ibatis.annotations.SelectProvider
import org.apache.ibatis.type.JdbcType
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider
import org.mybatis.dynamic.sql.util.SqlProviderAdapter
import org.mybatis.dynamic.sql.util.kotlin.SelectCompleter
import org.mybatis.dynamic.sql.util.kotlin.mybatis3.selectList
import org.mybatis.dynamic.sql.util.kotlin.mybatis3.selectOne


@Mapper
interface BookWithRentalMapper {
    //@SelectProvider   何をするためのメソッドなのかの指定
    @SelectProvider(type = SqlProviderAdapter::class, method = "select")
    //↓クエリの結果を受け取るオブジェクトとのマッピング
    @Results(
        id = "BookWithRentalResult",
        value = [ //jdbc データベースのデータ型との共通規格   ,id = true 主キーの設定
            Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            Result(column = "title", property = "title", jdbcType = JdbcType.VARCHAR),
            Result(column = "author", property = "author", jdbcType = JdbcType.VARCHAR),
            Result(column = "release_date", property = "releaseDate", jdbcType = JdbcType.DATE),
            Result(column = "user_id", property = "userId", jdbcType = JdbcType.BIGINT),
            Result(column = "rental_datetime", property = "rentalDatetime", jdbcType = JdbcType.TIMESTAMP),
            Result(column = "return_deadline", property = "returnDeadline", jdbcType = JdbcType.TIMESTAMP),
        ]

    )
    fun selectMany(selectStatementProvider: SelectStatementProvider)
    : List<BookWithRental>
    @SelectProvider(type = SqlProviderAdapter::class, method = "select")
    @ResultMap("BookWithRentalResult")
    fun selectOne(selectStatementProvider: SelectStatementProvider): BookWithRental?
}

private val columnList =  listOf(
    id,
    title,
    author,
    releaseDate,
    userId,
    rentalDatetime,
    returnDeadline
)

//拡張関数定義    WEHRE句などを後から追加するためのしくみ
fun BookWithRentalMapper.select(completer: SelectCompleter) =
    selectList(this::selectMany, columnList, book) {
        leftJoin(rental, "r") {
            on(id) equalTo rental.bookId
        }
        run(completer)
    }

//select * from book; の条件　
fun BookWithRentalMapper.select() = select {}

//SELECT * と同じように追加条件を指定せずすべて取り出すためのしくみ
fun BookWithRentalMapper.selectOne(completer: SelectCompleter) =
    selectOne(this::selectOne, columnList, book) {
        leftJoin(rental, "r"){
            on(id) equalTo rental.bookId
        }
        run(completer)
    }


fun BookWithRentalMapper.findByPrimaryKey(bookId: Long) =
    selectOne {
        where {
            id isEqualTo bookId
        }
    }
package com.shinhaedam.kotlinmoviereviewapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Movie::class, Review::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {

    abstract val movieDatabaseDao:MovieDao

    companion object {
        @Volatile
        private var INSTANCE:MovieDatabase? = null

        fun getInstance(context: Context): MovieDatabase {
            synchronized(this) {    // 동기화블럭 // 한 스레드만 진입가능
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MovieDatabase::class.java,
                        "movie_database"    // db명
                    )
                        .addCallback(object : RoomDatabase.Callback() {  // 데이터베이스 생성 시 fillInDb 호출
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                super.onCreate(db)
                                fillInDb(context.applicationContext)
                            }
                        })
                        .fallbackToDestructiveMigration()   // 마이그레이션 전략
                        .build()


                    INSTANCE = instance
                }
                return instance
            }
        }

        // 데이터 미리 채우기
        fun fillInDb(context: Context){
            CoroutineScope(Dispatchers.IO).launch {
                getInstance(context)!!.movieDatabaseDao.addMovieDb(
                    MOVIE_DATA
                )
                getInstance(context)!!.movieDatabaseDao.addReviewDb(
                    REVIEW_DATA
                )
            }
        }
    }
}

private val MOVIE_DATA = arrayListOf(
    Movie(title="해적-도깨비 깃발", openTime="2022.01.26", genre="어드벤처, 액션, 코미디", director="김정훈", actor="강하늘, 한효주, 이광수 등", showTime="125분"),
    Movie(title="킹메이커",openTime="2022.01.26", genre="드라마", director="변성현", actor="설경구, 이선균 등", showTime="123분"),
    Movie(title="스파이더맨-노 웨이 홈",openTime="2021.12.15", genre="액션, 어드벤처, SF", director="존 왓츠", actor="톰 홀랜드, 젠데이아 콜먼 등", showTime="148분"),
    Movie(title="씽2게더",openTime="2022.01.05", genre="애니메이션", director="가스 제닝스", actor="매튜 맥커너히 등", showTime="109분"),
    Movie(title="캐롤",openTime="2022.02.04", genre="드라마", director="토드 헤인즈", actor="케이트 블란쳇", showTime="118분"),
    Movie(title="어나더 라운드",openTime="2022.01.19", genre="드라마", director="토마스 빈터베르그", actor="매즈 미켈슨", showTime="116분"),
    Movie(title="하우스 오브 구찌",openTime="2022.01.12", genre="스릴러, 드라마, 범죄", director="리들리 스콧", actor="레이디 가가, 아담 드라이버, 자레드 레토 등", showTime="157분"),
)

private val REVIEW_DATA = arrayListOf(
    Review(grade=8.0, comment="너무 재밌어요", reviewer="해적왕",movie=1L),
    Review(grade=2.0, comment="보다가 잤네요... 불면증이면 보세요", reviewer="잠만보",movie=2L),
    Review(grade=7.5, comment="그냥 볼만합니다", reviewer="노-말",movie=2L),
    Review(grade=9.5, comment="최고입니다 그냥 보세요", reviewer="무너박사",movie=3L),
    Review(grade=1.0, comment="너무 유치함", reviewer="광운초1짱",movie=3L),
    Review(grade=9.5, comment="재밌네요~ 애기들도 집중해서 잘봤어요~", reviewer="거미맘",movie=3L),
    Review(grade=8.5, comment="영화도 재밌고 노래도 좋음", reviewer="남색",movie=4L),
    Review(grade=8.0, comment="저희...어머니가...보시고...즐거워하셨습니다...", reviewer="불꽃효자",movie=5L),
    Review(grade=8.5, comment="뭉클하네요....", reviewer="눈사람발로차",movie=5L),
    Review(grade=4.5, comment="굳이 볼만한 영화는 아닌듯", reviewer="그림그린그림",movie=6L),
    Review(grade=6.5, comment="재미없는건아닌데.. 전개가 너무 억지인듯", reviewer="SayMyName",movie=7L),
    Review(grade=9.5, comment="재밌어요 :)", reviewer="헥토파스칼",movie=7L),
)
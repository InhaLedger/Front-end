package com.inhaproject.karaoke3.retrofit

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.inhaproject.karaoke3.SearchData
import com.inhaproject.karaoke3.ui.community.board.PackArticleData
import com.inhaproject.karaoke3.ui.community.fixboard.FixArticleData
import com.inhaproject.karaoke3.ui.community.newboard.NewArticleData
import com.inhaproject.karaoke3.ui.community.noteboard.NoteArticleData
import com.inhaproject.karaoke3.ui.home.RankData
import com.inhaproject.karaoke3.ui.mypage.ProposalData
import com.inhaproject.karaoke3.ui.mypage.coin.BalanceData
import com.inhaproject.karaoke3.ui.mypage.coin.FindUserData
import com.inhaproject.karaoke3.ui.mypage.mynote.MyNoteData
import com.inhaproject.karaoke3.ui.mysong.BasketData
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface RetroInterface{

    // 로그인 , 회원가입 API
    @FormUrlEncoded
    @POST("/join")
    /*@Headers("accept: application/json",
        "content-type: application/json")*/
    fun register(
        //인풋을 정의하는 곳
        @Field("userid") userid:String,
        @Field("password") password:String
    ) : Call<Response<Void>> // 아웃풋을 정의하는곳

    @FormUrlEncoded
    @POST("/login")
    fun login(
        @Field("userid") userid:String,
        @Field("password") password:String
    ) : Call<LoginResult>

    //패키지 게시판 API

    @GET("/packboard")
    fun packBoardList(): Call<ArrayList<PackArticleData>>

    @GET("/packread")
    fun packRead(
        @Query("packidx") packidx:String
    ) : Call<ArrayList<PackArticleData>>


    @FormUrlEncoded
    @POST("/packwrite")
    fun packWrite(
        @Field("title") title:String,
        @Field("content") content:String,
        @Field("packlist") packlist:String,
        @Field("packprice") packprice:String
    ) : Call<Response<Void>>

    @GET("/rank")
    fun rankList(): Call<ArrayList<RankData>>

    @FormUrlEncoded
    @POST("/packvote")
    fun packVote(
        @Field("packidx") packidx: String,
        @Field("votetype") votetype: String
    ) : Call<String>

    @FormUrlEncoded
    @POST("/searchsong")
    fun searchSong(
        @Field("no") no : String,
        @Field("title") title: String,
        @Field("singer") singer: String,
        @Field("album") album : String,
        @Field("fromdate") fromdate : String,
        @Field("todate") todate : String,
        @Field("highNote") highNote : String,
        @Field("lowNote") lowNote : String
    ) : Call<ArrayList<SearchData>>

    // 음역대 게시판 API
    @FormUrlEncoded
    @POST("/mynote")
    fun myNoteSubmit(
        @Field("highNote") highNote: String,
        @Field("lowNote") lowNote: String
    ) : Call<String>

    @GET("/noteboard")
    fun noteBoardList() : Call<ArrayList<NoteArticleData>>

    @GET("/noteread")
    fun noteRead(
        @Query("noteidx") noteidx:String
    ) : Call<ArrayList<NoteArticleData>>

    @FormUrlEncoded
    @POST("/notewrite")
    fun noteWrite(
        @Field("no") no : String,
        @Field("title") title : String,
        @Field("content") content: String,
        @Field("highNote") highNote: String,
        @Field("lowNote") lowNote: String
    ): Call<String>

    @FormUrlEncoded
    @POST("/notevote")
    fun noteVote(
        @Field("noteidx") noteidx: String,
        @Field("votetype") votetype: String
    ) : Call<String>

    // 신곡 게시판 API
    @GET("/newboard")
    fun newBoardList() : Call<ArrayList<NewArticleData>>

    @GET("/newread")
    fun newRead(
        @Query("newidx") newidx:String
    ) : Call<ArrayList<NewArticleData>>

    @FormUrlEncoded
    @POST("/newwrite")
    fun newWrite(
        @Field("board_title") board_title : String,
        @Field("board_content") board_content : String,
        @Field("no") no : String,
        @Field("title") title : String,
        @Field("content") content : String,
        @Field("singer") singer : String,
        @Field("composer") composer : String,
        @Field("lyricist") lyricist : String,
        @Field("releasedate") releasedate : String,
        @Field("album") album : String,
        @Field("imageurl") imageurl : String,
    ): Call<String>

    @FormUrlEncoded
    @POST("/newvote")
    fun newVote(
        @Field("newidx") newidx: String,
        @Field("votetype") votetype: String
    ) : Call<String>

    //수정 게시판 API
    @GET("/fixboard")
    fun fixBoardList() : Call<ArrayList<FixArticleData>>

    @GET("/fixread")
    fun fixRead(
        @Query("fixidx") fixidx:String
    ) : Call<ArrayList<FixArticleData>>

    @FormUrlEncoded
    @POST("/fixwrite")
    fun fixWrite(
        @Field("board_title") board_title : String,
        @Field("board_content") board_content : String,
        @Field("no") no : String,
        @Field("title") title : String,
        @Field("content") content : String,
        @Field("singer") singer : String,
        @Field("composer") composer : String,
        @Field("lyricist") lyricist : String,
        @Field("releasedate") releasedate : String,
        @Field("album") album : String,
        @Field("imageurl") imageurl : String,
    ): Call<String>

    @FormUrlEncoded
    @POST("/fixvote")
    fun fixVote(
        @Field("fixidx") fixidx: String,
        @Field("votetype") votetype: String
    ) : Call<String>


    //장바구니 API
    @GET("/mysong")
    fun mySongList() : Call<ArrayList<BasketData>>

    @FormUrlEncoded
    @POST("/insertmysong")
    fun insertMySong(
        @Field("no") no : String
    ) : Call<String>

    @FormUrlEncoded
    @POST("/deletemysong")
    fun deleteMySong(
        @Field("no") no : String
    ) : Call<String>

    // 마이 페이지
    @GET("/showmynote")
    fun showMyNote() : Call<ArrayList<MyNoteData>>

    @GET("/mysearch")
    fun mySearch() : Call<ArrayList<SearchData>>

    @GET("/mycoin")
    fun myCoin() : Call<BalanceData>

    @GET("/myproposal")
    fun myProposal() : Call<ArrayList<ProposalData>>

    @FormUrlEncoded
    @POST("/sendcoin")
    fun sendCoin(
        @Field("receiverId") receiverId : String,
        @Field("amounts") amounts: String
    ) : Call<String>

    @FormUrlEncoded
    @POST("/finduser")
    fun findUser(
        @Field("userid") userid : String
    ) : Call<ArrayList<FindUserData>>

    //관리자 전용
    @GET("/admin_mycoin")
    fun adminMyCoin() : Call<BalanceData>

    @FormUrlEncoded
    @POST("/admin_sendcoin")
    fun adminSendCoin(
        @Field("receiverId") receiverId : String,
        @Field("amounts") amounts : String
    ) : Call<String>

    @FormUrlEncoded
    @POST("/admin_blockuser")
    fun blockUser(
        @Field("userid") userid : String
    ) : Call<String>

    @FormUrlEncoded
    @POST("/admin_issuecoin")
    fun issueCoin(
        @Field("amounts") amounts: String
    ) : Call<String>

    @GET("/admin_proposal")
    fun adminProposal() : Call<ArrayList<ProposalData>>

    @GET("/admin_finalize")
    fun finalize() : Call<String>


    companion object { // static 처럼 공유객체로 사용가능함. 모든 인스턴스가 공유하는 객체로서 동작함.
        private const val BASE_URL = "http://3.34.189.21:3000/" //

        fun create(): RetroInterface {
            val gson : Gson =   GsonBuilder().setLenient().create()

            val client = OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor())
                .build()

            return Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(RetroInterface::class.java)
        }
    }
}
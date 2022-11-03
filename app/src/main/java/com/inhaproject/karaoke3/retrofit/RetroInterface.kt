package com.inhaproject.karaoke3.retrofit

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded

interface RetroInterface{

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


    companion object { // static 처럼 공유객체로 사용가능함. 모든 인스턴스가 공유하는 객체로서 동작함.
        private const val BASE_URL = "http://52.79.214.156:3000" //

        fun create(): RetroInterface {
            val gson : Gson =   GsonBuilder().setLenient().create()

            val client = OkHttpClient.Builder().addInterceptor(AuthInterceptor()).build()

            return Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(RetroInterface::class.java)
        }
    }
}
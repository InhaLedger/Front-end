package com.inhaproject.karaoke3.retrofit

import android.util.Log
import com.inhaproject.karaoke3.preference.App
import com.inhaproject.karaoke3.preference.Prefs
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val jwtToken = App.prefs.token

        val req = chain.request().newBuilder()
            .addHeader("Authorization", "bearer $jwtToken").build()
        return chain.proceed(req)
    }
}
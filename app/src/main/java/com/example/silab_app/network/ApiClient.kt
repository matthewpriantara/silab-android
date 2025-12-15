package com.example.silab_app.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    fun getInstance(token: String): ApiService {
        val mHttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        val authInterceptor = okhttp3.Interceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $token").build()
            chain.proceed(request)
        }

        val mOkHttpClient = OkHttpClient.Builder().addInterceptor(mHttpLoggingInterceptor).addInterceptor(authInterceptor).build()


        val builder = Retrofit.Builder().baseUrl("https://be-lab-tedi.vercel.app/android/").addConverterFactory(
            GsonConverterFactory.create()).client(mOkHttpClient).build()

        return builder.create(ApiService::class.java)
    }
}
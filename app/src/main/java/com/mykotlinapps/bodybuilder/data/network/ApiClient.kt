package com.mykotlinapps.bodybuilder.data.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "https://exercisedb.p.rapidapi.com/"
    private const val API_KEY = "d163ce6c1fmsh29fe75841fce382p11d25ajsn126d51070987"
    private const val API_HOST = "exercisedb.p.rapidapi.com"

    private val client = OkHttpClient.Builder()
        .addInterceptor { chain: Interceptor.Chain ->
            val request: Request = chain.request().newBuilder()
                .addHeader("x-rapidapi-key", API_KEY)
                .addHeader("x-rapidapi-host", API_HOST)
                .build()
            chain.proceed(request)
        }
        .build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: ExerciseApiService by lazy {
        retrofit.create(ExerciseApiService::class.java)
    }
}



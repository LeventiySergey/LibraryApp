// RetrofitInstance.kt
package com.example.libraryapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.openai.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: ChatGPTService by lazy {
        retrofit.create(ChatGPTService::class.java)
    }

    private val googleBooksRetrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://www.googleapis.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val googleBooksApi: GoogleBooksService by lazy {
        googleBooksRetrofit.create(GoogleBooksService::class.java)
    }
}

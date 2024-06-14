package com.example.libraryapp.network

import com.example.libraryapp.BuildConfig
import com.example.libraryapp.data.ChatGPTRequest
import com.example.libraryapp.data.ChatGPTResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ChatGPTService {
    @Headers("Content-Type: application/json", "Authorization: Bearer API_KEY_HERE")
    @POST("v1/chat/completions")
    fun getChatGPTResponse(@Body request: ChatGPTRequest): Call<ChatGPTResponse>
}
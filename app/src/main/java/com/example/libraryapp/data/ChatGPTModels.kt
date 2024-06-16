package com.example.libraryapp.data

data class ChatGPTRequest(val model: String, val messages: List<Message>)
data class Message(val role: String, val content: String)
data class ChatGPTResponse(val choices: List<Choice>)
data class Choice(val message: Message)
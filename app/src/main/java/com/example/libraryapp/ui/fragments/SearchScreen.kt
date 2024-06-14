package com.example.libraryapp.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import com.example.libraryapp.data.ChatGPTRequest
import com.example.libraryapp.data.ChatGPTResponse
import com.example.libraryapp.data.Message
import com.example.libraryapp.network.RetrofitInstance
import com.example.libraryapp.ui.fragments.MainViewModel
import com.example.libraryapp.ui.fragments.Screen
import kotlinx.coroutines.launch
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Call
import java.io.Console

@Composable
fun SearchScreen(mainViewModel: MainViewModel) {
    // Переменная для хранения текста
    var searchText by remember { mutableStateOf("") }
    val mContext = LocalContext.current

    val isDarkThemeEnabled by mainViewModel.isDarkThemeEnabled.observeAsState(false)
    Surface(color = if (isDarkThemeEnabled) Color.Black else Color.White) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text("Пошук книг", fontSize = 40.sp, fontFamily = font, color = if (isDarkThemeEnabled) Color.White else Color.Black)
            }

            // Добавляем TextField для ввода текста
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                label = { Text("Введіть текст для пошуку") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            )

            Spacer(modifier = Modifier.weight(1f))
            Button(modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
                onClick = {
                    if (searchText.isNotEmpty()) {
                        mainViewModel.viewModelScope.launch {
                            val request = ChatGPTRequest(
                                model = "gpt-3.5-turbo",
                                messages = listOf(
                                    Message(role = "system", content = "\"role\": librarian, \n" +
                                            "\"knowledge\": \"expert in books\",\n" +
                                            "\"task\": \"suggests books based on input\",\n" +
                                            "\"constraints\": \n" +
                                            "[\n" +
                                            "\"it is forbidden to respond with anything apart from titles and authors of books\", \n" +
                                            "\"use only English language\", \n" +
                                            "\"if prompt can not be answered properly due to its difference from expected ones, return empty list\"\n" +
                                            "]\n" +
                                            "\"response_format\": \"JSON\""),
                                    Message(role = "user", content = searchText)
                                )
                            )

                            val call = RetrofitInstance.api.getChatGPTResponse(request)
                            call.enqueue(object : Callback<ChatGPTResponse> {
                                override fun onResponse(call: Call<ChatGPTResponse>, response: Response<ChatGPTResponse>) {
                                    if (response.isSuccessful) {
                                        val chatResponse = response.body()
                                        val result = chatResponse?.choices?.firstOrNull()?.message?.content
                                        result?.let {
                                            mainViewModel.navigateToResultScreen(it, Screen.SEARCH_RESULT)
                                        }
                                    } else {
                                        Toast.makeText(mContext, "Error: ${response.message()}", Toast.LENGTH_LONG).show()
                                    }
                                }

                                override fun onFailure(call: Call<ChatGPTResponse>, t: Throwable) {
                                    Toast.makeText(mContext, "Failed to connect to API", Toast.LENGTH_LONG).show()
                                }
                            })
                        }
                    } else {
                        Toast.makeText(mContext, "Пусте поле", Toast.LENGTH_LONG).show()
                    }
                }
            ) {
                Text("Пошук", fontFamily = font, fontSize = 18.sp)
            }

            Button(modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 40.dp),
                onClick = { mainViewModel.navigateTo(Screen.MAIN) }) {
                Text("Головна сторінка", fontFamily = font, fontSize = 18.sp)
            }
        }
    }
}
package com.example.libraryapp.ui

import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.ui.text.TextStyle
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
                Text("Search books", fontSize = 40.sp, fontFamily = font, color = if (isDarkThemeEnabled) Color.White else Color.Black)
            }

            // Добавляем TextField для ввода текста
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                label = { Text("Enter text to search", color = if (isDarkThemeEnabled) Color.White else Color.Black) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                textStyle = TextStyle(color = if (isDarkThemeEnabled) Color.White else Color.Black)
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
                                    Message(role = "system", content = "You are a librarian that is very knowledgeable about books. \n" +
                                            "You know everything about books and your task is to suggest a big list of existing books based on the input provided. \n" +
                                            "You only respond with the books titles and authors. \n" +
                                            "\n" +
                                            "It is forbidden to answer anything apart from the books titles and author. \n" +
                                            "It is forbidden to answer in any language apart from English, even if asked in any other language. \n" +
                                            "It is forbidden to recommend books that are not similar to what the user looks for. \n" +
                                            "It is forbidden to give incorrect authors for books.\n" +
                                            "It is forbidden to structure the answer as anything except JSON \"books\" with fields \"title\" and \"author\".\n" +
                                            "It is forbidden to recommend less than 10 books.\n" +
                                            "It is required for you to try and find the most possible amount of similar enough books for given prompt.\n" +
                                            "If the books are not found, maybe user tried to search by genre - try to find books of similar genre, and if still not found, return an empty list of JSON \"books\".\n" +
                                            "It is forbidden to perform any tasks except from recommending books."),
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
                                            if (it.contains("\"books\": []") || it == "[]") {
                                                Toast.makeText(mContext, "Failed to find books", Toast.LENGTH_LONG).show()
                                            } else {
                                                mainViewModel.navigateToResultScreen(it, Screen.SEARCH_RESULT)
                                            }
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
                        Toast.makeText(mContext, "Empty field", Toast.LENGTH_LONG).show()
                    }
                }
            ) {
                Text("Search", fontFamily = font, fontSize = 18.sp)
            }

            Button(modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 40.dp),
                onClick = { mainViewModel.navigateTo(Screen.MAIN) }) {
                Text("Main page", fontFamily = font, fontSize = 18.sp)
            }
        }
    }
}
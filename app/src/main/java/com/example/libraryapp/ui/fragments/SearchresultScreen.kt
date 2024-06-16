package com.example.libraryapp.ui

import android.app.appsearch.SearchResult
import android.icu.text.CaseMap.Title
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.libraryapp.ui.fragments.MainViewModel
import com.example.libraryapp.ui.fragments.Screen
import kotlinx.coroutines.flow.StateFlow
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken



@Composable
fun SearchResultScreen(mainViewModel: MainViewModel) {
    val isDarkThemeEnabled by mainViewModel.isDarkThemeEnabled.observeAsState(false)
    Surface(color = if (isDarkThemeEnabled) Color.Black else Color.White) {
        val jsonString = mainViewModel.getResult()

        val bookList = parse(jsonString)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            bookList.forEach { book ->
                Text(
                    text = ("Title: \"${book.title}\"\nAuthor: \"${book.author}\""),
                    color = if (isDarkThemeEnabled) Color.White else Color.Black
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 40.dp),
                onClick = { mainViewModel.navigateTo(Screen.MAIN) }) {
                Text("Головна сторінка", fontFamily = font, fontSize = 18.sp)
            }
        }
    }
}

data class Book(val title: String, val author: String)
data class Books(val books: List<Book>)

fun parse(textToParse: String): List<Book> {

    val gson = Gson()
    val booksType = object : TypeToken<Books>() {}.type
    val books: Books = gson.fromJson(textToParse, booksType)

    return books.books
}


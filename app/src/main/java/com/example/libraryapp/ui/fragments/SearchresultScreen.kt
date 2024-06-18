package com.example.libraryapp.ui

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.libraryapp.data.GoogleBooksResponse
import com.example.libraryapp.network.RetrofitInstance
import com.example.libraryapp.ui.fragments.MainViewModel
import com.example.libraryapp.ui.fragments.Screen
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Call



@Composable
fun SearchResultScreen(mainViewModel: MainViewModel) {
    val isDarkThemeEnabled by mainViewModel.isDarkThemeEnabled.observeAsState(false)
    Surface(color = if (isDarkThemeEnabled) Color.Black else Color.White) {
        val jsonString = mainViewModel.getResult()

        val bookList = parse(jsonString)

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp).padding(bottom=16.dp)
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Found:", fontSize = 40.sp, fontFamily = font, color = if (isDarkThemeEnabled) Color.White else Color.Black)
                }
            }
            items(bookList) { book ->
                BookCard(mainViewModel, book = book)
            }

            item {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 40.dp, top = 16.dp),
                    onClick = { mainViewModel.navigateTo(Screen.MAIN) }
                ) {
                    Text("Main page", fontFamily = font, fontSize = 18.sp)
                }
            }
        }
    }
}

@Composable
fun BookCard(mainViewModel: MainViewModel, book: Book) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        color = Color(android.graphics.Color.parseColor("#57a5bd")),
        shape = RoundedCornerShape(8.dp),
        onClick = {
            val googleBooksCall = RetrofitInstance.googleBooksApi.searchBooks("inauthor:${book.author}+intitle:${book.title}")
            googleBooksCall.enqueue(object : Callback<GoogleBooksResponse> {
                override fun onResponse(call: Call<GoogleBooksResponse>, response: Response<GoogleBooksResponse>) {
                    if (response.isSuccessful) {
                        val googleBooksResponse = response.body()
                        val books = googleBooksResponse?.items?.map {
                            mapOf(
                                "title" to it.volumeInfo.title,
                                "authors" to it.volumeInfo.authors,
                                "description" to it.volumeInfo.description,
                                "infoLink" to it.volumeInfo.infoLink,
                                "pageCount" to it.volumeInfo.pageCount,
                                "thumbnail" to it.volumeInfo.imageLinks?.thumbnail,
                                "categories" to it.volumeInfo.categories
                            )
                        }
                        mainViewModel.navigateToBookDetails(books?.get(0), Screen.BOOK_DETAILS)
                    } else {
                        Log.e("BookCard", "Failed to fetch book details. Error code: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<GoogleBooksResponse>, t: Throwable) {
                    Log.e("BookCard", "Network request failed", t)
                }
            })

        }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Назва: ${book.title}",
                color = Color.White,
                fontFamily = font
            )
            Text(
                text = "Автор: ${book.author}",
                modifier = Modifier.padding(top = 8.dp),
                color = Color.White,
                fontFamily = font
            )
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




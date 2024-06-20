package com.example.libraryapp.ui.fragments

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.request.ImageRequest
import coil.request.ImageRequest.Builder
import coil.request.ImageResult
import coil.compose.rememberAsyncImagePainter
import android.util.Log
import coil.compose.rememberImagePainter
import com.example.libraryapp.data.DatabaseHandler
import com.example.libraryapp.ui.Books
import com.example.libraryapp.ui.font

@Composable
fun BookDetailsScreen(mainViewModel: MainViewModel) {
    val isDarkThemeEnabled by mainViewModel.isDarkThemeEnabled.observeAsState(false)
    val bookDetails = mainViewModel.getBookDetails()
    val context = LocalContext.current

    Surface(color = if (isDarkThemeEnabled) Color.Black else Color.White) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Book details",
                        fontSize = 40.sp,
                        fontFamily = font,
                        color = if (isDarkThemeEnabled) Color.White else Color.Black
                    )
                }
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                ) {
                    Image(
                        painter = rememberImagePainter(

                            ImageRequest.Builder(LocalContext.current)
                                .data(bookDetails?.get("thumbnail").toString())
                                .listener(
                                    onError = { request, throwable ->
                                        Log.e("Coil Image Loading: ", "Error loading image:"  + throwable.throwable.message)
                                    },
                                    onSuccess = { request, metadata ->
                                        Log.i("Coil Image Loading", "Image loaded successfully")
                                    }
                                )
                                .build()

                        )
                        ,
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .size(150.dp)
                            .padding(end = 16.dp)
                    )


                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 16.dp)
                    ) {
                        Text(
                            text = bookDetails?.get("title").toString(),
                            fontSize = 24.sp,
                            fontFamily = font,
                            color = if (isDarkThemeEnabled) Color.White else Color.Black
                        )
                        Text(
                            text = bookDetails?.get("authors").toString(),
                            fontSize = 18.sp,
                            fontFamily = font,
                            color = if (isDarkThemeEnabled) Color.White else Color.Black
                        )
                        Text(
                            text = bookDetails?.get("categories").toString(),
                            fontSize = 18.sp,
                            fontFamily = font,
                            color = if (isDarkThemeEnabled) Color.White else Color.Black
                        )
                        Text(
                            text = "Page count: " + bookDetails?.get("pageCount").toString(),
                            fontSize = 18.sp,
                            fontFamily = font,
                            color = if (isDarkThemeEnabled) Color.White else Color.Black
                        )
                    }
                }
            }
            item {
                Text(
                    text = bookDetails?.get("description").toString(),
                    fontSize = 16.sp,
                    fontFamily = font,
                    color = if (isDarkThemeEnabled) Color.White else Color.Black,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                )
            }
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 40.dp)
                ) {
                    Button(
                        onClick = {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(bookDetails?.get("infoLink").toString()))
                            context.startActivity(intent)
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("More information", fontFamily = font, fontSize = 18.sp)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            val authors = bookDetails?.get("authors") as? List<String> ?: emptyList()
                            val book = com.example.libraryapp.data.Books(
                                bookDetails?.get("title").toString(),
                                authors.joinToString(",")
                            )
                            var db = DatabaseHandler(context)

                            if(!db.isBookInFavorites(book.title)) {
                                db.insertData(book)
                            }
                            else {
                                db.removeData(book.title)
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        var db = DatabaseHandler(context)
                        if (db.isBookInFavorites(bookDetails?.get("title").toString())) {
                            Text("Remove from favourites", fontFamily = font, fontSize = 18.sp)
                        }
                        else {
                            Text("Add to favourites", fontFamily = font, fontSize = 18.sp)
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { if(mainViewModel.getResult().isNotEmpty())
                                    {
                                        mainViewModel.navigateTo(Screen.SEARCH_RESULT)
                                    }
                                    else
                                    {
                                        mainViewModel.navigateTo(Screen.FAVORITES)
                                    }
                                  },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Back", fontFamily = font, fontSize = 18.sp)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { mainViewModel.navigateTo(Screen.MAIN) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Main page", fontFamily = font, fontSize = 18.sp)
                    }
                }
            }
        }
    }
}

package com.example.libraryapp.ui.fragments

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.example.libraryapp.ui.font

@Composable
fun BookDetailsScreen(mainViewModel: MainViewModel) {
    val isDarkThemeEnabled by mainViewModel.isDarkThemeEnabled.observeAsState(false)
    val bookDetails = mainViewModel.getBookDetails()

    println(bookDetails)
    println(bookDetails?.get("title"))
    println(bookDetails?.get("authors"))
    println(bookDetails?.get("description"))
    println(bookDetails?.get("infoLink"))
    println(bookDetails?.get("thumbnail"))

}
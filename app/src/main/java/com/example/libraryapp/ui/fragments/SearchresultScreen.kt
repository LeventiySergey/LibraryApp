package com.example.libraryapp.ui

import android.app.appsearch.SearchResult
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
import com.example.libraryapp.ui.fragments.NavigationViewModel
import com.example.libraryapp.ui.fragments.Screen


@Composable
fun SearchResultScreen(navigationViewModel: NavigationViewModel) {
    val isDarkThemeEnabled by navigationViewModel.isDarkThemeEnabled.observeAsState(false)

    val text = navigationViewModel.getResult()

    Button(modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 40.dp),
        onClick = { navigationViewModel.navigateTo(Screen.MAIN) }) {
        Text("Головна сторінка", fontFamily = font, fontSize = 18.sp)
    }
}
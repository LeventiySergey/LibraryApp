package com.example.libraryapp.ui

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
fun SearchScreen(navigationViewModel: NavigationViewModel) {
    // Переменная для хранения текста
    val searchText = remember { mutableStateOf("") }

    Surface(color = Color.White) {
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
                Text("Пошук книг", fontSize = 40.sp, fontFamily = font)
            }

            // Добавляем TextField для ввода текста
            OutlinedTextField(
                value = searchText.value,
                onValueChange = { searchText.value = it },
                label = { Text("Введіть текст для пошуку") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)
            )

            Spacer(modifier = Modifier.weight(1f))
            Button(modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
                onClick = { /* Ваше действие для поиска, например, использовать searchText.value */ }) {
                Text("Пошук", fontFamily = font, fontSize = 18.sp)
            }
            Button(modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
                onClick = { navigationViewModel.navigateTo(Screen.MAIN) }) {
                Text("Головна сторінка", fontFamily = font, fontSize = 18.sp)
            }
        }
    }
}

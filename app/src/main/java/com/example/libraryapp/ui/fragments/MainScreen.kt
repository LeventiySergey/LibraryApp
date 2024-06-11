// MainScreen.kt
package com.example.libraryapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.libraryapp.R
import com.example.libraryapp.ui.fragments.NavigationViewModel
import com.example.libraryapp.ui.fragments.Screen

@Composable
fun MainScreen(navigationViewModel: NavigationViewModel) {
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
                Text("Головна сторінка", fontSize = 40.sp, fontFamily = font)
            }
            Image(modifier = Modifier.fillMaxWidth(), painter = painterResource(id = R.drawable.logo), contentDescription = "logo")
            Spacer(modifier = Modifier.weight(1f))
            Button(modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
                onClick = { navigationViewModel.navigateTo(Screen.SEARCH) }) {
                Text("Пошук книг", fontFamily = font, fontSize = 18.sp)
            }
            Button(modifier = Modifier
                .fillMaxWidth()
                .padding(bottom=10.dp),
                onClick = { navigationViewModel.navigateTo(Screen.FAVORITES) }) {
                Text("Улюблені книги", fontFamily = font, fontSize = 18.sp)
            }
            Button(modifier = Modifier
                .fillMaxWidth()
                .padding(bottom=40.dp),
                onClick = { navigationViewModel.navigateTo(Screen.SETTINGS) }) {
                Text("Налаштування", fontFamily = font, fontSize = 18.sp)
            }
        }
    }
}

val font = FontFamily(
    Font(R.font.freakyfont)
)

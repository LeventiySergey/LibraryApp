// MainScreen.kt
package com.example.libraryapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.libraryapp.R
import com.example.libraryapp.ui.fragments.MainViewModel
import com.example.libraryapp.ui.fragments.Screen

@Composable
fun MainScreen(mainViewModel: MainViewModel) {
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
                Text("Main page", fontSize = 40.sp, fontFamily = font, color = if (isDarkThemeEnabled) Color.White else Color.Black)
            }
            Image(modifier = Modifier.fillMaxWidth(), painter = painterResource(id = if (isDarkThemeEnabled) R.drawable.logodark else R.drawable.logo), contentDescription = "logo")
            Spacer(modifier = Modifier.weight(1f))
            Button(modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
                onClick = { mainViewModel.navigateTo(Screen.SEARCH) }) {
                Text("Search books", fontFamily = font, fontSize = 18.sp)
            }
            Button(modifier = Modifier
                .fillMaxWidth()
                .padding(bottom=10.dp),
                onClick = { mainViewModel.navigateTo(Screen.FAVORITES) }) {
                Text("Favourites", fontFamily = font, fontSize = 18.sp)
            }
            Button(modifier = Modifier
                .fillMaxWidth()
                .padding(bottom=40.dp),
                onClick = { mainViewModel.navigateTo(Screen.SETTINGS) }) {
                Text("Settings", fontFamily = font, fontSize = 18.sp)
            }
        }
    }
}

val font = FontFamily(
    Font(R.font.freakyfont)
)

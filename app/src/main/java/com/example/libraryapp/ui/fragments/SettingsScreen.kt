// SettingsScreen.kt
package com.example.libraryapp.ui

import android.widget.Switch
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.libraryapp.ui.fragments.MainViewModel
import com.example.libraryapp.ui.fragments.Screen

@Composable
fun SettingsScreen(mainViewModel: MainViewModel) {
    val isDarkThemeEnabled by mainViewModel.isDarkThemeEnabled.observeAsState(false)
    Surface(color = if (isDarkThemeEnabled) Color.Black else Color.White){
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
                Text("Settings", fontSize = 40.sp, fontFamily = font, color = if (isDarkThemeEnabled) Color.White else Color.Black)
            }
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Dark theme",
                    fontSize = 24.sp,
                    fontFamily = font,
                    color = if (isDarkThemeEnabled) Color.White else Color.Black,
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Switch(
                    checked = isDarkThemeEnabled,
                    onCheckedChange = { mainViewModel.toggleDarkTheme() }
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 40.dp),
                onClick = { mainViewModel.navigateTo(Screen.MAIN) }) {
                Text("Main page", fontFamily = font, fontSize = 18.sp)
            }
        }
    }
}
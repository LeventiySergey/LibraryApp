package com.example.libraryapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.example.libraryapp.ui.fragments.MainViewModel
import com.example.libraryapp.ui.fragments.Screen
import com.example.libraryapp.ui.theme.LibraryAppTheme

class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LibraryAppTheme {
                val currentScreen by mainViewModel.currentScreen.observeAsState(Screen.MAIN)
                when (currentScreen) {
                    Screen.MAIN -> MainScreen(mainViewModel)
                    Screen.SEARCH -> SearchScreen(mainViewModel)
                    Screen.FAVORITES -> FavoritesScreen(mainViewModel)
                    Screen.SETTINGS -> SettingsScreen(mainViewModel)
                }
            }
        }
    }
}

package com.example.libraryapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.example.libraryapp.ui.fragments.NavigationViewModel
import com.example.libraryapp.ui.fragments.Screen
import com.example.libraryapp.ui.theme.LibraryAppTheme

class MainActivity : ComponentActivity() {
    private val navigationViewModel: NavigationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LibraryAppTheme {
                val currentScreen by navigationViewModel.currentScreen.observeAsState(Screen.MAIN)
                when (currentScreen) {
                    Screen.MAIN -> MainScreen(navigationViewModel)
                    Screen.SEARCH -> SearchScreen()
                    Screen.FAVORITES -> FavoritesScreen()
                    Screen.SETTINGS -> SettingsScreen()
                }
            }
        }
    }
}

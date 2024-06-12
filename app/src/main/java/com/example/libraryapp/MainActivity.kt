package com.example.libraryapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.text.input.TextFieldValue
import com.example.libraryapp.ui.fragments.NavigationViewModel
import com.example.libraryapp.ui.fragments.Screen
import com.example.libraryapp.ui.theme.LibraryAppTheme

class MainActivity : ComponentActivity() {
    private val navigationViewModel: NavigationViewModel by viewModels()
    lateinit var searchText : TextFieldValue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LibraryAppTheme {
                val currentScreen by navigationViewModel.currentScreen.observeAsState(Screen.MAIN)
                when (currentScreen) {
                    Screen.MAIN -> MainScreen(navigationViewModel)
                    Screen.SEARCH -> SearchScreen(navigationViewModel)
                    Screen.FAVORITES -> FavoritesScreen(navigationViewModel)
                    Screen.SETTINGS -> SettingsScreen(navigationViewModel)
                    Screen.SEARCH_RESULT -> SearchResultScreen(navigationViewModel)
                }
            }
        }
    }
}

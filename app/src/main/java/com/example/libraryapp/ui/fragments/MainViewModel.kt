package com.example.libraryapp.ui.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _currentScreen = MutableLiveData<Screen>()
    val currentScreen: LiveData<Screen> get() = _currentScreen

    private val _isDarkThemeEnabled = MutableLiveData(false)
    val isDarkThemeEnabled: LiveData<Boolean> get() = _isDarkThemeEnabled

    fun toggleDarkTheme() {
        _isDarkThemeEnabled.value = !_isDarkThemeEnabled.value!!
    }

    fun navigateTo(screen: Screen) {
        _currentScreen.value = screen
    }
}


enum class Screen {
    MAIN,
    SEARCH,
    FAVORITES,
    SETTINGS
}

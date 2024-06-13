package com.example.libraryapp.ui.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.w3c.dom.Text

class MainViewModel : ViewModel() {
    private val _currentScreen = MutableLiveData<Screen>()
    val currentScreen: LiveData<Screen> get() = _currentScreen

    private val _isDarkThemeEnabled = MutableLiveData(false)
    val isDarkThemeEnabled: LiveData<Boolean> get() = _isDarkThemeEnabled

    private var resultText = ""

    fun toggleDarkTheme() {
        _isDarkThemeEnabled.value = !_isDarkThemeEnabled.value!!
    }

    fun navigateTo(screen: Screen) {
        _currentScreen.value = screen
    }

    fun navigateToResultScreen(text: String, screen: Screen) {
        resultText = text
        _currentScreen.value = screen
    }

    fun getResult(): String {
        return this.resultText
    }
}


enum class Screen {
    MAIN,
    SEARCH,
    FAVORITES,
    SETTINGS,
    SEARCH_RESULT
}

package com.example.libraryapp.ui.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NavigationViewModel : ViewModel() {
    private val _currentScreen = MutableLiveData<Screen>()
    val currentScreen: LiveData<Screen> get() = _currentScreen

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

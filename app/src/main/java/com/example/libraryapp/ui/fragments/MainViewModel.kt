package com.example.libraryapp.ui.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.libraryapp.data.BookItem
import com.example.libraryapp.data.GoogleBooksResponse
import com.example.libraryapp.network.GoogleBooksService
import org.w3c.dom.Text

class MainViewModel : ViewModel() {
    private val _currentScreen = MutableLiveData<Screen>()
    val currentScreen: LiveData<Screen> get() = _currentScreen

    private val _isDarkThemeEnabled = MutableLiveData(false)
    val isDarkThemeEnabled: LiveData<Boolean> get() = _isDarkThemeEnabled

    private var resultText = ""

    private var book: Map<String, Any?>? = null

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

    fun navigateToBookDetails(book: Map<String, Any?>?, screen: Screen) {
        this.book = book
        _currentScreen.value = screen
    }

    fun getResult(): String {
        return this.resultText
    }

    fun getBookDetails(): Map<String, Any?>? {
        return this.book
    }
}


enum class Screen {
    MAIN,
    SEARCH,
    FAVORITES,
    SETTINGS,
    SEARCH_RESULT,
    BOOK_DETAILS
}

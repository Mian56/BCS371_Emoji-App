package edu.farmingdale.datastoredemo.ui

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import edu.farmingdale.datastoredemo.EmojiReleaseApplication
import edu.farmingdale.datastoredemo.R
import edu.farmingdale.datastoredemo.data.local.UserPreferencesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class EmojiScreenViewModel(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    // State for managing theme (light/dark mode)
    private val _isDarkMode = mutableStateOf(false) // MutableState to store the theme
    val isDarkMode: State<Boolean> = _isDarkMode

    init {
        // Load the saved theme preference from DataStore on initialization
        loadThemePreference()
    }

    // Load the theme preference from DataStore
    private fun loadThemePreference() {
        viewModelScope.launch {
            // Fetch the theme preference from DataStore and set it
            _isDarkMode.value = userPreferencesRepository.loadThemePerference()
        }
    }

    // Function to toggle between light and dark mode
    fun toggleTheme() {
        val newTheme = !_isDarkMode.value
        _isDarkMode.value = newTheme
        // Save the theme preference to DataStore
        saveThemePreference(newTheme)
    }

    // Save the theme preference in DataStore
    private fun saveThemePreference(isDarkMode: Boolean) {
        viewModelScope.launch {
            userPreferencesRepository.saveThemePreference(isDarkMode)
        }
    }

    // UI state handling layout type (linear/grid) -- Unchanged
    val uiState: StateFlow<EmojiReleaseUiState> =
        userPreferencesRepository.isLinearLayout.map { isLinearLayout ->
            EmojiReleaseUiState(isLinearLayout)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = EmojiReleaseUiState()
        )

    fun selectLayout(isLinearLayout: Boolean) {
        viewModelScope.launch {
            userPreferencesRepository.saveLayoutPreference(isLinearLayout)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as EmojiReleaseApplication)
                EmojiScreenViewModel(application.userPreferencesRepository)
            }
        }
    }
}


/*
 * Data class containing various UI States for Emoji Release screens
 */
data class EmojiReleaseUiState(
    val isLinearLayout: Boolean = true,
    val toggleContentDescription: Int =
        if (isLinearLayout) R.string.grid_layout_toggle else R.string.linear_layout_toggle,
    val toggleIcon: Int =
        if (isLinearLayout) R.drawable.ic_grid_layout else R.drawable.ic_linear_layout
)

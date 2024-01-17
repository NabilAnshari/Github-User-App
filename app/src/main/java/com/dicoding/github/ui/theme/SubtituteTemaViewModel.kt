package com.dicoding.github.ui.theme

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SubtituteTemaViewModel(private val pref: SettingPreferences) : ViewModel() {

    fun getTemaSubtution(): LiveData<Boolean> {
        return pref.getThemeSubstitute().asLiveData()
    }

    fun saveTemaSubtution(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSubstitute(isDarkModeActive)
        }
    }
}
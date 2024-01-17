package com.dicoding.github.ui.theme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.dicoding.restaurantreview.databinding.ActivityThemeBinding

class SubtituteTemaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityThemeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThemeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val switchThemeSubstitution = binding.switchTheme
        val preference = SettingPreferences.getInstance(application.dataStore)
        val subtituteTemaViewModel = ViewModelProvider(this, ViewModelFactory(preference))[SubtituteTemaViewModel::class.java]
        // Observe perubahan didalam theme subtitution dan update ui
        subtituteTemaViewModel.getTemaSubtution().observe(this) { isDarkModeActive: Boolean ->
            //set night mode berdasarkan observed value
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchThemeSubstitution.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchThemeSubstitution.isChecked = false
            }
        }

        switchThemeSubstitution.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            subtituteTemaViewModel.saveTemaSubtution(isChecked)
            Toast.makeText(this, "Theme switched: $isChecked", Toast.LENGTH_SHORT).show()
        }
    }
}
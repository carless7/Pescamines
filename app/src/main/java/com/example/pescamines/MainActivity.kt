package com.example.pescamines

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.example.pescamines.data.AppDatabase
import com.example.pescamines.data.UserPreferencesRepository
import com.example.pescamines.navigation.AppNavigation
import com.example.pescamines.ui.theme.AppColors
import com.example.pescamines.viewmodel.GameViewModel
import com.example.pescamines.viewmodel.GameViewModelFactory

class MainActivity : ComponentActivity() {
    private lateinit var gameViewModel: GameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Obtén la instància de la base de dades i el DAO
        val database = AppDatabase.getDatabase(this)
        val gameDao = database.gameDao()

        // Crea la instància de UserPreferencesRepository
        val userPreferencesRepository = UserPreferencesRepository(this)

        // Crea el ViewModelFactory
        val viewModelFactory = GameViewModelFactory(gameDao, userPreferencesRepository)
        gameViewModel = ViewModelProvider(this, viewModelFactory)[GameViewModel::class.java]

        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = AppColors.Background
            ) {
                AppNavigation(gameViewModel)
            }
        }
    }
}

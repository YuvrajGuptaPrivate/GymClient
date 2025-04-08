package com.example.gymclient.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.gymclient.presentation.util.MyNavigation
import com.example.gymclient.presentation.theme.GymClientTheme
import com.example.gymclient.presentation.viewmodels.AuthenticatioViewModel
import com.example.gymclient.presentation.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val authenticatioViewModel : AuthenticatioViewModel by viewModels()
        val homeViewModel : HomeViewModel by viewModels()

        setContent {
            GymClientTheme {
                Scaffold(modifier = Modifier.Companion.fillMaxSize()) { innerPadding ->
                    MyNavigation(
                        modifier = Modifier.Companion
                            .padding(innerPadding),
                        authenticatioViewModel, homeViewModel , innerPadding
                    )
                }
            }
        }
    }
}
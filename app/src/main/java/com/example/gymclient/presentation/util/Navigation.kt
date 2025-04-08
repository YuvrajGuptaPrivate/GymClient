package com.example.gymclient.presentation.util

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.example.gymclient.presentation.screens.HomeScreen
import com.example.gymclient.presentation.screens.LoginScreen
import com.example.gymclient.presentation.viewmodels.AuthenticatioViewModel
import com.example.gymclient.presentation.viewmodels.HomeViewModel


@Composable
fun MyNavigation(
    modifier: Modifier = Modifier,
    authenticatioViewModel: AuthenticatioViewModel,
    homeViewModel: HomeViewModel,
    paddingValues: PaddingValues
){

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login" ,
        builder = {
            composable(route = "login"){
                LoginScreen(
                    navController = navController,
                    modifier = modifier,
                    viewmodel = authenticatioViewModel
                )
            }
            composable(route = "home"){
                HomeScreen(
                    homeViewModel, modifier, paddingValues
                )
            }
        }
    )


}
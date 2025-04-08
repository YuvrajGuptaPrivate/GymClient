package com.example.gymclient.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.gymclient.presentation.viewmodels.HomeViewModel


@Composable
fun HomeScreen(homeViewModel: HomeViewModel, modifier: Modifier, paddingValues1: PaddingValues) {


    var selectedTab by remember<MutableState<Int>> { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar()
        },
        bottomBar = {
            BottomNavigationBar(selectedTab, onTabSelected = { selectedTab = it })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (selectedTab) {
                0 -> Dashboard(modifier = modifier)
                1 -> AttendanceScreen(homeViewModel,paddingValues1)
                2 -> WorkoutScreen(homeViewModel)
            }



        }
    }
}

@Composable
fun BottomNavigationBar(selectedTab: Int, onTabSelected: (Int) -> Unit) {
    NavigationBar {
        val items = listOf(
            Pair("Home", Icons.Filled.Home),
            Pair("Attendance", Icons.Filled.DateRange),
            Pair("WorkOuts", Icons.Filled.FitnessCenter)
        )

        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(imageVector = item.second, contentDescription = item.first) },
                label = { Text(item.first) },
                selected = selectedTab == index,
                onClick = { onTabSelected(index) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(){
    CenterAlignedTopAppBar(
        title = { Text(text = "Gym Ease", color = MaterialTheme.colorScheme.onSurface, modifier =
            Modifier.fillMaxWidth().padding(7.5.dp),
            textAlign = TextAlign.Left) },
        actions = {
            IconButton(onClick = { }){
                Icon(imageVector = (Icons.Default.Menu),
                    contentDescription = "Menu button")

            }
        }
        
    )
}



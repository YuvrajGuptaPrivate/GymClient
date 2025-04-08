package com.example.gymclient.presentation.screens


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Update
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gymclient.R
import com.example.gymclient.data.model.DayEntity
import com.example.gymclient.data.model.ExerciseEntity
import com.example.gymclient.data.model.WorkoutPlanEntity
import com.example.gymclient.presentation.viewmodels.HomeViewModel

@Composable
fun WorkoutScreen(viewModel: HomeViewModel){

    val workoutPlan = remember { "Muscle Gain Plan" }


    Column(modifier = Modifier
        .padding(7.5.dp)
    ) {
        Text(text = workoutPlan, style = MaterialTheme.typography.displaySmall , modifier = Modifier.padding(start = 16.dp))
        Spacer(Modifier.height(7.5.dp))
        AttendanceProgress()

        Text(text = "Today's Workouts", style = MaterialTheme.typography.bodyLarge , modifier = Modifier.padding(start = 16.dp))

        LazyColumn {
            items(7){
                WorkoutItem()
            }
        }

    }

}


@Composable
fun AttendanceProgress() {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(25.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onSecondary) // Correct way to set background
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(text = "Your Progress", fontSize = 16.sp)

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(), // Ensures full width
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween // Better spacing than SpaceEvenly
            ) {
                CircularItem("21")
                CircularItem("22")
                CircularItem("23")
                CircularItem("24")
                CircularItem("25")
                CircularItem("26")
                CircularItem("27")
            }

            Spacer(modifier = Modifier.height(16.dp)) // Adds consistent spacing before next section

            CardStats()
        }
    }
}


@Composable
fun CircularItem(date: String){
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(36.dp)
            .background(color = MaterialTheme.colorScheme.onSecondary,shape = CircleShape)
            .border(BorderStroke(1.dp , color = MaterialTheme.colorScheme.secondary), shape = RoundedCornerShape(25.dp))
    ) {
        Text(text = date)
    }
}

@Composable
fun CardStats(){
    Card(modifier = Modifier.fillMaxWidth()
        .background( color = MaterialTheme.colorScheme.onSecondary, shape = RoundedCornerShape(25.dp)),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
    ) {
        Row(modifier = Modifier.padding(1.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            CardItemStats(value = "18", title = "Workout")
            CardItemStats(value = "10645", title = "Kcal")
            CardItemStats(value = "30:54", title = "Minute")
        }
    }
}
@Composable
fun CardItemStats(value : String, title : String){
    Column(modifier = Modifier.padding(7.5.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = value, style = MaterialTheme.typography.displaySmall)
        Text(text = title, style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
fun WorkoutItem(){
    Card(modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, top = 7.5.dp, bottom = 7.5.dp)
        .background( color = MaterialTheme.colorScheme.onSecondary, shape = RoundedCornerShape(25.dp)),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
    ) {
        Row(modifier = Modifier.padding(1.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = painterResource(id = R.drawable.workout1),
                contentDescription = "workoutSample",
                modifier = Modifier
                    .size(width = 95.dp, height = 78.dp)
                    .clip(RoundedCornerShape(25.dp))
            )

            WorkOutItemInside()

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(36.dp)
                    .background(color = MaterialTheme.colorScheme.onSecondary,shape = CircleShape)
                    .border(BorderStroke(1.dp , color = MaterialTheme.colorScheme.secondary), shape = RoundedCornerShape(25.dp))
            ) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                    contentDescription = "icon to start workout")
            }

        }
    }
}

@Composable
fun WorkOutItemInside(){
    Column {
        Text(text = "Bench Press", fontSize = 16.sp )

        Row(modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.Update, contentDescription = "icon", modifier = Modifier.size(20.dp))
            Text(text = "18", fontSize = 12.sp)
            Text(text = " Minute", fontSize = 12.sp)
        }
    }
}

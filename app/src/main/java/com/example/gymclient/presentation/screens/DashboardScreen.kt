package com.example.gymclient.presentation.screens


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LineWeight
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.Percent
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gymclient.presentation.theme.Typography

@Composable
fun Dashboard(modifier: Modifier){

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(7.5.dp)
    ){


       Row(modifier = Modifier.padding(start = 7.5.dp, end = 7.5.dp),
           horizontalArrangement = Arrangement.Center)
       {
            CircularProgressBar(
                modifier = modifier,
                progress = 0.56f
            )
           ProgressBarData()
       }

        Text(text = "Attendance Streak", fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier.padding(7.5.dp))

        val sampleAttendance = listOf(true, false, true, true, true, true, false) // Example data
        AttendanceStreakCard(sampleAttendance)

        Text(text = "Quick Operations", fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier.padding(7.5.dp))

        QuickOperations()


    }
}





@Composable
fun CircularProgressBar(
    progress: Float, // Value between 0f and 1f
    modifier: Modifier = Modifier,
    size: Dp = 225.dp,
    strokeWidth: Dp = 12.dp
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(size)
            .padding(7.5.dp)
    ) {
        Canvas(modifier = Modifier.size(size)) {
            val stroke = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
            val startAngle = -90f // Starts from the top

            // Background Arc
            drawArc(
                color = Color.DarkGray,
                startAngle = startAngle,
                sweepAngle = 360f,
                useCenter = false,
                style = stroke
            )

            // Progress Arc
            drawArc(
                color = Color(0xFFFFD700), // Gold/Yellow color like the image
                startAngle = startAngle,
                sweepAngle = progress * 360f,
                useCenter = false,
                style = stroke
            )
        }

        // Text in the center
        Text(
            text = "${(progress * 100).toInt()}",
            style = Typography.displayMedium,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ProgressBarData(
    time : String = "1h 30min",
    trainingStyle : String = "Powerlifting",
    workouts : String = "10"
    ){
    Column(modifier = Modifier
        .padding(20.dp)
        .fillMaxWidth()
        ,verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        ProgressBarDateItem(title = "Training style", value = trainingStyle, itsColor = Color(0xFF4CAF50))

        Spacer(modifier = Modifier.height(25.dp))

        ProgressBarDateItem(title = "Duration", value = time, itsColor = Color(0xFFFFD700))

        Spacer(modifier = Modifier.height(25.dp))

        ProgressBarDateItem(title = "Exercises", value = workouts, itsColor = Color(0xFF9C27B0))

    }
}

@Composable
fun ProgressBarDateItem(title : String, value : String,itsColor : Color){
    Text(text = value, style = Typography.bodyLarge)
    Text(text = title, style = Typography.bodyMedium,color = itsColor)
}




@Composable
fun AttendanceStreakCard(attendanceList: List<Boolean>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(7.5.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    ) {
        LazyRow(modifier = Modifier.fillMaxWidth()  ,horizontalArrangement = Arrangement.SpaceEvenly) {
            items(7){
                DashAttendanceItem(day = "Sun", date = "21")
            }
        }

    }
}


@Composable
fun DashAttendanceItem(date:String , day : String){
    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = day)
        Text(text = date, color = Color.DarkGray)
    }
}




@Composable
fun QuickOperations() {
    val cardItems = listOf(
        Pair(Icons.Default.QrCodeScanner, "Gym Check In"),
        Pair(Icons.Default.NotificationsNone, "Reminders"),
        Pair(Icons.Default.Percent, "Get BMI"),
        Pair(Icons.Default.LineWeight, "Current Weight")
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        for (i in cardItems.indices step 2) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                DashboardCard(icon = cardItems[i].first, text = cardItems[i].second, modifier = Modifier.weight(1f))

                if (i + 1 < cardItems.size) {
                    DashboardCard(icon = cardItems[i + 1].first, text = cardItems[i + 1].second, modifier = Modifier.weight(1f))
                } else {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun DashboardCard(icon: ImageVector, text: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(imageVector = icon, contentDescription = text)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = text)
        }
    }
}


@Composable
fun Header(){
    Row(modifier = Modifier
        .padding(7.5.dp)
        .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "Today",
            style = Typography.bodyLarge)
        Text(text = "GymEase",
            style = Typography.bodyLarge)
        Icon(imageVector = (Icons.Filled.Settings),
            contentDescription = "Setting Icon")

    }
}
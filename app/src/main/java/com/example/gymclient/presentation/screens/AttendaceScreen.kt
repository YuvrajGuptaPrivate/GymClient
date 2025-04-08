package com.example.gymclient.presentation.screens

import androidx.compose.runtime.Composable

import androidx.compose.material3.Text
import androidx.compose.ui.unit.sp
import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowForwardIos

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.gymclient.presentation.util.AttendanceItem
import com.example.gymclient.presentation.util.generateMonthDates
import com.example.gymclient.presentation.util.toLocalDate
import com.example.gymclient.presentation.viewmodels.HomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Month
import java.time.Year
import java.time.YearMonth
import java.time.ZoneId.systemDefault
import java.util.*

@Composable
fun AttendanceScreen(viewModel : HomeViewModel,paddingValues: PaddingValues) {

    var month by remember { mutableStateOf(YearMonth.now()) }

    val attendanceMap by viewModel.attendanceMap.collectAsState()

    Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(paddingValues)) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = {
                        month = month.minusMonths(1)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBackIosNew,
                        contentDescription = "Previous Month"
                    )
                }

                Text(
                    text = "${month.month.name.lowercase().replaceFirstChar { it.uppercase() }} ${month.year}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                IconButton(
                    onClick = {
                        month = month.plusMonths(1)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowForwardIos,
                        contentDescription = "Next Month"
                    )
                }
            }


            Row(modifier = Modifier.fillMaxWidth()
                , horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(text = "Sun", fontWeight = FontWeight.Bold)
                Text(text = "Mon", fontWeight = FontWeight.Bold)
                Text(text = "Tue", fontWeight = FontWeight.Bold)
                Text(text = "Wed", fontWeight = FontWeight.Bold)
                Text(text = "Thu", fontWeight = FontWeight.Bold)
                Text(text = "Fri", fontWeight = FontWeight.Bold)
                Text(text = "Sat", fontWeight = FontWeight.Bold)
            }

            CalenarView(
                month = month.monthValue,
                year = month.year,
                attendanceMap = attendanceMap
            )

        }


    }







@Composable
fun CalenarView(month : Int , year : Int , attendanceMap: Map<LocalDate, Boolean>){

    val calendarDays = remember(month,year) {
        generateMonthDates(
            month = month,
            year = year
        )
    }
    LazyVerticalGrid(
        columns = GridCells.Fixed(7),
        contentPadding = PaddingValues(start = 7.5.dp, end = 7.5.dp, top = 10.dp, bottom = 10.dp)
    ) {
        items(calendarDays) { date ->
            val isPresent = date.date?.let{ attendanceMap[it] } == true
            CircularBox(
                date = date.date?.dayOfMonth.toString()?:" ",
                isPresent = isPresent
            )
        }
    }
}


@Composable
fun CircularBox(date: String, isPresent : Boolean){
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(4.dp)
            .size(36.dp)
            .background(
                color = if (!isPresent) {
                    MaterialTheme.colorScheme.errorContainer
                } else {
                    Color(0xFF4CAF50)
                }, shape = CircleShape
            )
            .border(
                BorderStroke(1.dp, color = MaterialTheme.colorScheme.secondary),
                shape = RoundedCornerShape(25.dp)
            )
    ) {
        Text(text = date)
    }
}









fun showDatePicker(context: Context, onDateSelected: (Date) -> Unit) {
    val calendar = Calendar.getInstance()
    DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val selectedCalendar = Calendar.getInstance()
            selectedCalendar.set(Calendar.YEAR, year)
            selectedCalendar.set(Calendar.MONTH, month)
            selectedCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            selectedCalendar.set(Calendar.HOUR_OF_DAY, 0)
            selectedCalendar.set(Calendar.MINUTE, 0)
            selectedCalendar.set(Calendar.SECOND, 0)
            selectedCalendar.set(Calendar.MILLISECOND, 0)

            val selectedDate = selectedCalendar.time // This is a java.util.Date object
            onDateSelected(selectedDate)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ).show()
}

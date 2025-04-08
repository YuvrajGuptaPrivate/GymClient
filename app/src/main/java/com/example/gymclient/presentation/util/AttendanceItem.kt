package com.example.gymclient.presentation.util

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.gymclient.data.model.AttendanceRecord
import com.example.gymclient.data.model.LocalAttendanceRecord

@Composable
fun AttendanceItem(record: LocalAttendanceRecord) {
    Card(modifier = Modifier.fillMaxWidth().padding(8.dp), elevation = CardDefaults.cardElevation(4.dp)) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
        //    Text(text = record.clientname, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
            Text(text = if (record.status == "Present") "Present" else "Absent", color = if (record.status == "Present") Color.Green else Color.Red)
        }
    }
}
package com.edudesk.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edudesk.ui.components.TopNavigationBar

@Composable
fun AssignmentsScreen() {
    Scaffold(
        topBar = { TopNavigationBar() }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFF7F9FA)) // Light background
                .padding(horizontal = 48.dp)
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    "Manager Tugas",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2D2F31)
                )
                Spacer(modifier = Modifier.weight(1f))
                Button(onClick = {}, shape = RoundedCornerShape(4.dp)) {
                    Text("Tambah Tugas")
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Filters (Simple example)
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                FilterChip(selected = true, onClick = {}, label = { Text("Semua") })
                FilterChip(selected = false, onClick = {}, label = { Text("Belum Selesai") })
                FilterChip(selected = false, onClick = {}, label = { Text("Selesai") })
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Assignment Cards List
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 32.dp)
            ) {
                val mockAssignments = listOf(
                    Triple("Tugas Akhir AI", "Kecerdasan Buatan", "2026-03-10"),
                    Triple("Ujian Tengah Semester", "Struktur Data", "2026-03-15"),
                    Triple("Laporan Mingguan", "Mobile Programming", "2026-03-12"),
                    Triple("Review Jurnal", "Metodologi Penelitian", "2026-03-20")
                )
                
                items(mockAssignments) { (title, course, date) ->
                    AssignmentItem(title, course, date)
                }
            }
        }
    }
}

@Composable
fun AssignmentItem(title: String, course: String, deadline: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(course, fontSize = 14.sp, color = Color(0xFF6A6F73))
            }
            
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.DateRange, 
                    contentDescription = null, 
                    tint = Color(0xFF6A6F73),
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(deadline, fontSize = 14.sp, color = Color(0xFF6A6F73))
            }
            
            Spacer(modifier = Modifier.width(32.dp))
            
            IconButton(onClick = {}) {
                Icon(
                    Icons.Default.CheckCircle, 
                    contentDescription = "Mark as done",
                    tint = Color(0xFFD1D7DC)
                )
            }
        }
    }
}

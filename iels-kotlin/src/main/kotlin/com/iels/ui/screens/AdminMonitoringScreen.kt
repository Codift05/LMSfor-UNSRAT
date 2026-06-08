package com.iels.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iels.models.ExamSessionDto
import com.iels.services.ExamService
import com.iels.ui.components.AdminLayout
import kotlinx.coroutines.launch

@Composable
fun AdminMonitoringScreen() {
    val coroutineScope = rememberCoroutineScope()
    val examService = remember { ExamService() }
    var sessions by remember { mutableStateOf<List<ExamSessionDto>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    fun refreshData() {
        isLoading = true
        coroutineScope.launch {
            sessions = examService.getSessions()
            isLoading = false
        }
    }

    LaunchedEffect(Unit) {
        refreshData()
    }

    AdminLayout(activeMenu = "Monitoring") {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 48.dp, vertical = 32.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Live Monitoring Ujian", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Pantau aktivitas mahasiswa yang sedang melangsungkan ujian secara real-time.", color = Color(0xFF64748B), fontSize = 15.sp)
                }
                
                Button(
                    onClick = { refreshData() },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF496E96), contentColor = Color.White)
                ) {
                    Text("Refresh Data", fontWeight = FontWeight.SemiBold)
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                elevation = CardDefaults.cardElevation(0.dp)
            ) {
                Column(modifier = Modifier.fillMaxWidth().padding(32.dp)) {
                    if (isLoading) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                    } else if (sessions.isEmpty()) {
                        Text("Saat ini tidak ada ujian yang sedang berlangsung.", fontSize = 14.sp, color = Color(0xFF94A3B8))
                    } else {
                        // Header
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("NAMA MAHASISWA", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF94A3B8), modifier = Modifier.weight(2f))
                            Text("NIM", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF94A3B8), modifier = Modifier.weight(1.5f))
                            Text("UJIAN", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF94A3B8), modifier = Modifier.weight(2f))
                            Text("STATUS", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF94A3B8), modifier = Modifier.weight(1f))
                        }
                        
                        Divider(color = Color(0xFFF1F5F9))
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        sessions.forEach { session ->
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(session.userName, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color(0xFF0F172A), modifier = Modifier.weight(2f))
                                Text(session.userNim, fontSize = 14.sp, color = Color(0xFF64748B), modifier = Modifier.weight(1.5f))
                                Text(session.examTitle, fontSize = 14.sp, color = Color(0xFF0F172A), modifier = Modifier.weight(2f))
                                
                                Box(modifier = Modifier.weight(1f)) {
                                    Surface(
                                        shape = RoundedCornerShape(16.dp),
                                        color = Color(0xFFDBEAFE) // Light blue
                                    ) {
                                        Text(
                                            "Sedang Mengerjakan",
                                            color = Color(0xFF1E40AF), // Dark blue
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                        )
                                    }
                                }
                            }
                            Divider(color = Color(0xFFF1F5F9))
                        }
                    }
                }
            }
        }
    }
}

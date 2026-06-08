package com.iels.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iels.models.Exam
import com.iels.models.AdminStatsDto
import com.iels.services.ExamService
import com.iels.ui.components.AdminLayout
import kotlinx.coroutines.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.Canvas
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.layout.ContentScale

@Composable
fun AdminOverviewScreen() {
    val coroutineScope = rememberCoroutineScope()
    val examService = remember { ExamService() }
    var exams by remember { mutableStateOf<List<Exam>>(emptyList()) }
    var stats by remember { mutableStateOf<AdminStatsDto?>(null) }

    fun refreshData() {
        coroutineScope.launch {
            exams = examService.getExams()
            stats = examService.getAdminStats()
        }
    }

    LaunchedEffect(Unit) {
        refreshData()
    }

    AdminLayout(activeMenu = "Dashboard") {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 48.dp, vertical = 32.dp)
        ) {
            // HEADER BANNER
            Surface(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource("Banner Web IELS 2.png"),
                    contentDescription = "Iels Banner",
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.FillWidth
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            // STATS ROW
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                StatCard(
                    title = "Total Ujian Aktif",
                    value = exams.size.toString(),
                    icon = Icons.Default.Assignment,
                    color = Color(0xFF496E96),
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "Total Peserta Ujian",
                    value = stats?.totalStudents?.toString() ?: "0",
                    icon = Icons.Default.Groups,
                    color = Color(0xFF10B981),
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "Rata-rata Kelulusan",
                    value = stats?.let { String.format("%.1f%%", it.averageScore) } ?: "0.0%",
                    icon = Icons.Default.TrendingUp,
                    color = Color(0xFFF59E0B),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(48.dp))

            // ANALYTICS CHARTS
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                elevation = CardDefaults.cardElevation(0.dp)
            ) {
                Column(modifier = Modifier.fillMaxWidth().padding(32.dp)) {
                    Text("Tingkat Kelulusan Rata-Rata", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Persentase rata-rata skor kelulusan siswa seluruh ujian.", fontSize = 14.sp, color = Color(0xFF64748B))
                    Spacer(modifier = Modifier.height(32.dp))
                    
                    val average = stats?.averageScore?.toFloat() ?: 0f
                    val passColor = Color(0xFF10B981)
                    val failColor = Color(0xFFEF4444)
                    
                    Canvas(modifier = Modifier.fillMaxWidth().height(40.dp)) {
                        val canvasWidth = size.width
                        val canvasHeight = size.height
                        
                        val passWidth = canvasWidth * (average / 100f)
                        val failWidth = canvasWidth - passWidth
                        
                        // Pass Bar
                        drawRect(
                            color = passColor,
                            topLeft = Offset(0f, 0f),
                            size = Size(passWidth, canvasHeight)
                        )
                        
                        // Fail Bar
                        drawRect(
                            color = failColor,
                            topLeft = Offset(passWidth, 0f),
                            size = Size(failWidth, canvasHeight)
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(color = passColor, shape = RoundedCornerShape(4.dp), modifier = Modifier.size(12.dp)) {}
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Lulus (${String.format("%.1f", average)}%)", fontSize = 13.sp, color = Color(0xFF64748B))
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(color = failColor, shape = RoundedCornerShape(4.dp), modifier = Modifier.size(12.dp)) {}
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Tidak Lulus (${String.format("%.1f", 100f - average)}%)", fontSize = 13.sp, color = Color(0xFF64748B))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            // ACTIVE EXAMS LIST
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                elevation = CardDefaults.cardElevation(0.dp)
            ) {
                Column(modifier = Modifier.fillMaxWidth().padding(32.dp)) {
                    Text("Daftar Ujian Aktif", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    if (exams.isEmpty()) {
                        Text("Belum ada ujian yang diterbitkan.", fontSize = 14.sp, color = Color(0xFF94A3B8))
                    } else {
                        exams.forEach { exam ->
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = Color(0xFFF8FAFC),
                                border = BorderStroke(1.dp, Color(0xFFF1F5F9)),
                                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth().padding(24.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        val displayTitle = exam.title.takeIf { it.isNotBlank() } ?: "(Tanpa Judul)"
                                        Text(displayTitle, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF0F172A))
                                        Spacer(modifier = Modifier.height(8.dp))
                                        
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            if (exam.token.isNotBlank()) {
                                                Surface(color = Color(0xFFE0E7FF), shape = RoundedCornerShape(6.dp)) {
                                                    Text(exam.token, color = Color(0xFF4338CA), fontWeight = FontWeight.Bold, fontSize = 11.sp, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                                                }
                                                Spacer(modifier = Modifier.width(12.dp))
                                            }
                                            Text("${exam.durationMinutes} Menit", color = Color(0xFF64748B), fontSize = 13.sp, fontWeight = FontWeight.Medium)
                                            Spacer(modifier = Modifier.width(12.dp))
                                            Text("${exam.questions.size} Soal", color = Color(0xFF64748B), fontSize = 13.sp, fontWeight = FontWeight.Medium)
                                        }
                                    }
                                    
                                    Button(
                                        onClick = { /* TBD: View Results / Analytics */ },
                                        shape = RoundedCornerShape(100),
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF496E96), contentColor = Color.White)
                                    ) {
                                        Text("Lihat Hasil", fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = Color.White)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StatCard(title: String, value: String, icon: ImageVector, color: Color, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.height(110.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = color.copy(alpha = 0.1f),
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(icon, contentDescription = null, tint = color, modifier = Modifier.padding(8.dp))
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(title, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF64748B))
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(value, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A), letterSpacing = (-0.5).sp)
                }
            }
        }
    }
}

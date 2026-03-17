package com.edudesk.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edudesk.ui.components.TopNavigationBar
import com.edudesk.ui.navigation.NavController
import com.edudesk.ui.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseDetailScreen() {
    val scrollState = rememberScrollState()
    var isEnrolled by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { TopNavigationBar() }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFE2E8EF))
                .verticalScroll(scrollState)
        ) {
            // Course Banner/Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {
                Image(
                    painter = painterResource("banner matkul 2.png"),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.4f))
                )
                
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(horizontal = 48.dp, vertical = 32.dp)
                ) {
                    TextButton(
                        onClick = { NavController.navigateTo(Screen.CourseRegistration) },
                        contentPadding = PaddingValues(start = 0.dp, end = 16.dp),
                        colors = ButtonDefaults.textButtonColors(contentColor = Color.White)
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null, modifier = Modifier.size(24.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Kembali", fontSize = 18.sp, fontWeight = FontWeight.Medium, color = Color.White)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "Pemrograman Berorientasi Objek",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = CircleShape,
                            color = Color.White.copy(alpha = 0.2f),
                            modifier = Modifier.size(32.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(Icons.Default.Person, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                            }
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Dr. Eng. Rizal, S.T., M.T.", color = Color.LightGray, fontSize = 16.sp)
                    }
                }
            }

            // Main Content
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 48.dp, vertical = 32.dp),
                horizontalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                // Left side: Description and Syllabus
                Column(modifier = Modifier.weight(2f)) {
                    Text("Tentang Mata Kuliah", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "Mata kuliah ini membahas konsep dasar pemrograman berorientasi objek menggunakan bahasa Java. " +
                        "Topik yang dibahas meliputi class, object, inheritance, polymorphism, encapsulation, dan abstraction. " +
                        "Mahasiswa akan belajar membangun aplikasi yang modular, reusable, dan mudah di-maintain.",
                        lineHeight = 24.sp,
                        color = Color(0xFF475569)
                    )

                    Spacer(modifier = Modifier.height(32.dp))
                    Text("Silabus Pembelajaran", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    val syllabus = listOf(
                        "Pengenalan Pemrograman Berorientasi Objek",
                        "Instalasi Tools dan Basic Syntax Java",
                        "Class dan Object dalam Java",
                        "Inheritance (Pewarisan)",
                        "Polymorphism (Polimorfisme)",
                        "Interface dan Abstract Class",
                        "Exception Handling",
                        "Java GUI dan Database Connection"
                    )

                    syllabus.forEachIndexed { index, item ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp)
                                .clickable { NavController.navigateToLesson(index) },
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp, hoveredElevation = 4.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0))
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .background(Color(0xFFEFF4FB), RoundedCornerShape(8.dp)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("${index + 1}", fontWeight = FontWeight.Bold, color = Color(0xFF496E96), fontSize = 14.sp)
                                }
                                Spacer(modifier = Modifier.width(14.dp))
                                Text(item, color = Color(0xFF1E293B), modifier = Modifier.weight(1f))
                                Icon(Icons.Default.PlayCircle, contentDescription = null, tint = Color(0xFF496E96))
                            }
                        }
                    }
                }

                // Right side: Quick Info and Action
                Column(modifier = Modifier.weight(1f)) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFDDE3EA)),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(modifier = Modifier.padding(24.dp)) {
                            Text("Pendaftaran", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(24.dp))
                            
                            InfoRow(Icons.Default.AccessTime, "Durasi", "1 Semester")
                            InfoRow(Icons.Default.MenuBook, "Jumlah Modul", "12 Modul")
                            InfoRow(Icons.Default.EmojiEvents, "Sertifikat", "Tersedia")
                            InfoRow(Icons.Default.CheckCircle, "Status", if (isEnrolled) "Terdaftar" else "Tersedia")

                            Spacer(modifier = Modifier.height(32.dp))
                            
                                Button(
                                    onClick = { isEnrolled = true },
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = if (isEnrolled) Color(0xFF22C55E) else Color(0xFF496E96),
                                        contentColor = Color.White
                                    ),
                                    contentPadding = PaddingValues(vertical = 16.dp),
                                    enabled = !isEnrolled
                                ) {
                                    Text(
                                        if (isEnrolled) "Sudah Terdaftar" else "Daftar Sekarang",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                        color = Color.White
                                    )
                                }
                            
                            if (isEnrolled) {
                                Spacer(modifier = Modifier.height(16.dp))
                                OutlinedButton(
                                    onClick = { NavController.navigateTo(Screen.Home) },
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(12.dp),
                                    contentPadding = PaddingValues(vertical = 12.dp)
                                ) {
                                    Text("Buka Kelas", fontWeight = FontWeight.Bold)
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
fun InfoRow(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, value: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(bottom = 16.dp)
    ) {
        Icon(icon, contentDescription = null, tint = Color(0xFF64748B), modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(label, fontSize = 12.sp, color = Color(0xFF64748B))
            Text(value, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF1E293B))
        }
    }
}

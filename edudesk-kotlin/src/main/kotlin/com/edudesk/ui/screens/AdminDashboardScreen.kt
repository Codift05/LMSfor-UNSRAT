package com.edudesk.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edudesk.ui.components.DashboardBanner
import com.edudesk.ui.components.TopNavigationBar

import androidx.compose.runtime.*
import com.edudesk.models.Course
import com.edudesk.models.User
import com.edudesk.services.CourseService
import com.edudesk.services.UserService
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboardScreen() {
    val coroutineScope = rememberCoroutineScope()
    val userService = remember { UserService() }
    val courseService = remember { CourseService() }

    var users by remember { mutableStateOf<List<User>>(emptyList()) }
    var courses by remember { mutableStateOf<List<Course>>(emptyList()) }

    fun refreshData() {
        coroutineScope.launch {
            users = userService.getUsers()
            courses = courseService.getAllCourses()
        }
    }

    LaunchedEffect(Unit) {
        refreshData()
    }

    Scaffold(
        topBar = { TopNavigationBar() }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFF8FAFC)) // Slate 50
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 48.dp, vertical = 24.dp)
        ) {
            DashboardBanner("Selamat Datang Kembali, Admin Prodi")
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Statistics Cards
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                StatCard("Total Pengguna", users.size.toString(), Modifier.weight(1f))
                StatCard("Total Kursus Aktif", courses.count { it.status == "approved" }.toString(), Modifier.weight(1f))
                StatCard("Total Sesi Hari Ini", (users.size * 2).toString(), Modifier.weight(1f)) // Mock active sessions
            }
            
            Spacer(modifier = Modifier.height(48.dp))
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(32.dp)) {
                // Left Column: Manajemen Pengguna
                Column(modifier = Modifier.weight(1.5f)) {
                    Text("Manajemen Pengguna", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(2.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(24.dp)) {
                            // Header Tab style
                            Row(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)) {
                                Text("Semua Pengguna", fontWeight = FontWeight.Bold, color = Color(0xFF6366F1), modifier = Modifier.padding(end = 24.dp))
                                Spacer(modifier = Modifier.weight(1f))
                                Text("Kelola", fontWeight = FontWeight.Medium, color = Color(0xFF8B5CF6))
                            }
                            Divider(color = Color(0xFFE2E8F0))
                            
                            // Rows
                            users.forEach { user ->
                                UserRow(
                                    name = user.name,
                                    role = user.role.capitalize(),
                                    status = if (user.isActive) "Aktif" else "Nonaktif",
                                    isActive = user.isActive,
                                    onToggleStatus = {
                                        coroutineScope.launch {
                                            userService.updateUserStatus(user.id, !user.isActive)
                                            refreshData()
                                        }
                                    }
                                )
                            }
                            if (users.isEmpty()) {
                                Text("Belum ada pengguna terdaftar.", color = Color.Gray, modifier = Modifier.padding(vertical = 16.dp))
                            }
                        }
                    }
                }
                
                // Right Column: Persetujuan Kelas
                Column(modifier = Modifier.weight(1f)) {
                    Text("Persetujuan Kelas", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(2.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            val pendingCourses = courses.filter { it.status == "pending" }
                            
                            if (pendingCourses.isEmpty()) {
                                Text("Tidak ada kelas yang menunggu persetujuan.", color = Color.Gray, modifier = Modifier.padding(vertical = 16.dp))
                            } else {
                                pendingCourses.forEach { course ->
                                    PendingClassRow(
                                        title = course.name,
                                        instructor = course.instructorName,
                                        onApprove = {
                                            coroutineScope.launch {
                                                courseService.updateCourseStatus(course.id, "approved")
                                                refreshData()
                                            }
                                        },
                                        onReject = {
                                            coroutineScope.launch {
                                                courseService.updateCourseStatus(course.id, "rejected")
                                                refreshData()
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(48.dp))
            
            // Laporan Sistem
            Text("Laporan Sistem (7 Hari Terakhir)", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Pertumbuhan Pendaftaran", fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Box(
                        modifier = Modifier.fillMaxWidth().height(250.dp).background(Color(0xFFF1F5F9), RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Wide Line Chart Placeholder (Pendaftaran vs Kelulusan)", color = Color.Gray)
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(64.dp))
        }
    }
}

@Composable
fun UserRow(name: String, role: String, status: String, isActive: Boolean, onToggleStatus: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier.size(36.dp),
            shape = CircleShape,
            color = Color(0xFFE2E8F0)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(name.take(1).uppercase(), fontWeight = FontWeight.Bold, color = Color(0xFF475569))
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(name, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Text(role, color = Color.Gray, fontSize = 12.sp)
        }
        
        Surface(
            color = if (isActive) Color(0xFFDCFCE7) else Color(0xFFFEE2E2), // Green vs Red Tint
            shape = RoundedCornerShape(4.dp)
        ) {
            Text(
                status,
                color = if (isActive) Color(0xFF16A34A) else Color(0xFFDC2626),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }
        
        Spacer(modifier = Modifier.width(16.dp))
        TextButton(onClick = onToggleStatus) {
            Text(if (isActive) "Blokir" else "Aktifkan", color = if (isActive) Color(0xFFDC2626) else Color(0xFF16A34A))
        }
    }
    Divider(color = Color(0xFFF1F5F9))
}

@Composable
fun PendingClassRow(title: String, instructor: String, onApprove: () -> Unit, onReject: () -> Unit) {
    Column(modifier = Modifier.padding(bottom = 16.dp)) {
        Text(title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        Text("Oleh: $instructor", color = Color.Gray, fontSize = 12.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = onApprove,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF16A34A),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(4.dp),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp),
                modifier = Modifier.height(32.dp)
            ) {
                Text("Setujui", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
            OutlinedButton(
                onClick = onReject,
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFDC2626)),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFDC2626)),
                shape = RoundedCornerShape(4.dp),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp),
                modifier = Modifier.height(32.dp)
            ) {
                Text("Tolak", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Divider(color = Color(0xFFF1F5F9))
    }
}

package com.edudesk.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edudesk.ui.components.CourseCard
import com.edudesk.ui.components.DashboardBanner
import com.edudesk.ui.components.TopNavigationBar
import com.edudesk.ui.navigation.NavController
import com.edudesk.ui.navigation.Screen

@Composable
fun HomeScreen() {
    Scaffold(topBar = { TopNavigationBar() }) { padding ->
        Column(
                modifier =
                        Modifier.fillMaxSize()
                                .padding(padding)
                                .background(Color(0xFFF8FAFC)) // Slate 50
                                .verticalScroll(rememberScrollState())
                                .padding(horizontal = 48.dp, vertical = 24.dp)
        ) {
            DashboardBanner("Selamat Datang Kembali, Miftahuddin S. Arsyad")

            Spacer(modifier = Modifier.height(16.dp))

            Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                            "Pendaftaran Kelas",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1E293B)
                    )
                    TextButton(onClick = { NavController.navigateTo(Screen.CourseRegistration) }) {
                        Text("Lihat Semua", color = Color(0xFF496E96), fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Horizontal Row of Courses
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    CourseCard(
                            title = "Pemrograman Berorientasi Objek",
                            instructor = "Dr. Eng. Rizal, S.T., M.T.",
                            rating = 5f,
                            reviewsCount = 244,
                            modifier = Modifier.weight(1f),
                            onClick = { NavController.navigateTo(Screen.CourseDetail) }
                    )
                    CourseCard(
                            title = "Struktur Data",
                            instructor = "Prof. Dr. Ir. Budi, M.Sc.",
                            rating = 5f,
                            reviewsCount = 299,
                            modifier = Modifier.weight(1f),
                            onClick = { NavController.navigateTo(Screen.CourseDetail) }
                    )
                    CourseCard(
                            title = "Kecerdasan Buatan",
                            instructor = "Prof. Dr. Ir. Budi, M.Sc.",
                            rating = 5f,
                            reviewsCount = 398,
                            isPremium = true,
                            modifier = Modifier.weight(1f),
                            onClick = { NavController.navigateTo(Screen.CourseDetail) }
                    )
                    CourseCard(
                            title = "Pengembangan Aplikasi Web",
                            instructor = "Ir. Maria, S.Kom., M.Kom.",
                            rating = 5f,
                            reviewsCount = 396,
                            modifier = Modifier.weight(1f),
                            onClick = { NavController.navigateTo(Screen.CourseDetail) }
                    )
                }

                Spacer(modifier = Modifier.height(40.dp))

                // Two Column Layout
                Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(32.dp)
                ) {
                    // Left Column
                    Column(modifier = Modifier.weight(1.2f)) {
                        Text("Mata Kuliah Saya", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
                        Spacer(modifier = Modifier.height(20.dp))

                        // My Courses List
                        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                            MyCourseListItem("Pemrograman Berorientasi Objek", 0.8f)
                            MyCourseListItem("Struktur Data", 0.4f)
                            MyCourseListItem("Kecerdasan Buatan", 0.2f)
                            MyCourseListItem("Jaringan Komputer Lanjut", 0.6f)
                        }

                        Spacer(modifier = Modifier.height(40.dp))

                        Text(
                                "Tugas Selesai & Tertunda",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1E293B)
                        )
                        Spacer(modifier = Modifier.height(20.dp))

                        // Task Accordion / Dropdowns
                        Card(
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                shape = RoundedCornerShape(16.dp),
                                elevation = CardDefaults.cardElevation(2.dp),
                                modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(20.dp)) {
                                Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text("Status Tugas", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                    Button(
                                            onClick = {},
                                            colors =
                                                    ButtonDefaults.buttonColors(
                                                            containerColor = Color(0xFF496E96),
                                                            contentColor = Color.White
                                                    ), // IELS Blue
                                            shape = RoundedCornerShape(10.dp),
                                            contentPadding =
                                                    PaddingValues(
                                                            horizontal = 20.dp,
                                                            vertical = 10.dp
                                                    )
                                    ) { Text("Upload", fontWeight = FontWeight.Bold, color = Color.White) }
                                }

                                Spacer(modifier = Modifier.height(20.dp))

                                TaskStatusDropdown("Tugas Selesai")
                                TaskStatusDropdown("Tugas Seminggu")
                                TaskStatusDropdown("Tugas Selesai & Tertunda")
                                TaskStatusDropdown("Tugas Selain Tertunda")
                                TaskStatusDropdown("Rapor Saya")
                            }
                        }
                    }

                    // Right Column
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Ujian Mandatang", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
                        Spacer(modifier = Modifier.height(20.dp))

                        // Upcoming Exams Cards
                        Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
                            ExamCard("Kerjakan Ujian/Kuis")
                            ExamCard("Kerjakan Ujian/Kuis\nQuizze 2")
                            ExamCard("Kerjakan Ujian/Kuis\nQuizze 3")
                        }

                        Spacer(modifier = Modifier.height(40.dp))

                        Text("Rapor Saya", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
                        Spacer(modifier = Modifier.height(20.dp))

                        // Report Card
                        Card(
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                shape = RoundedCornerShape(16.dp),
                                elevation = CardDefaults.cardElevation(2.dp),
                                modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(20.dp)) {
                                Text("Summary", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                Spacer(modifier = Modifier.height(16.dp))
                                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                    SummaryBadge("Grade\n6+", Color(0xFF496E96)) // Blue tint
                                    SummaryBadge("Grade\n8+", Color(0xFF496E96)) // Blue tint
                                    SummaryBadge("Grade\n7+", Color(0xFF1E293B)) // Dark Slate tint
                                }

                                Spacer(modifier = Modifier.height(32.dp))
                                Text("Performance", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                Spacer(modifier = Modifier.height(12.dp))

                                // Placeholder for Chart
                                Box(
                                        modifier =
                                                Modifier.fillMaxWidth()
                                                        .height(180.dp)
                                                        .background(
                                                                Color(0xFFF8FAFC),
                                                                RoundedCornerShape(12.dp)
                                                        ),
                                        contentAlignment = Alignment.Center
                                ) { Text("Line Chart Canvas Placeholder", color = Color.LightGray) }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(64.dp))
            }
        }
    }
}

// Subcomponents specifically for the Home Screen
@Composable
fun MyCourseListItem(title: String, progress: Float) {
    val courseIcon: ImageVector = when {
        title.contains("Pemrograman", ignoreCase = true) -> Icons.Default.Code
        title.contains("Data", ignoreCase = true) -> Icons.Default.Storage
        title.contains("Kecerdasan", ignoreCase = true) || title.contains("AI", ignoreCase = true) -> Icons.Default.Psychology
        title.contains("Web", ignoreCase = true) -> Icons.Default.Language
        else -> Icons.Default.Book
    }

    Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(2.dp),
            modifier = Modifier.fillMaxWidth()
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                    modifier =
                            Modifier.size(80.dp, 54.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color(0xFF496E96)),
                    contentAlignment = Alignment.Center
            ) {
                Icon(courseIcon, contentDescription = null, tint = Color.White, modifier = Modifier.size(24.dp))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color(0xFF1E293B))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    repeat(5) {
                        Icon(
                                Icons.Default.Star,
                                contentDescription = null,
                                tint = Color(0xFFFBBF24),
                                modifier = Modifier.size(12.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("${(progress * 100).toInt()}% Selesai", fontSize = 11.sp, color = Color.Gray, fontWeight = FontWeight.Medium)
                }
                Spacer(modifier = Modifier.height(10.dp))
                LinearProgressIndicator(
                        progress = progress,
                        modifier =
                                Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(4.dp)),
                        color = Color(0xFF496E96), // IELS Blue
                        trackColor = Color(0xFFF1F5F9)
                )
            }
        }
    }
}

@Composable
fun TaskStatusDropdown(text: String) {
    Surface(
            color = Color(0xFFF8FAFC),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0))
    ) {
        Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF334155))
            Icon(Icons.Default.KeyboardArrowDown, contentDescription = null, tint = Color(0xFF64748B))
        }
    }
}

@Composable
fun ExamCard(title: String) {
    Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(2.dp),
            modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(title, fontSize = 17.sp, fontWeight = FontWeight.Bold, lineHeight = 24.sp, color = Color(0xFF1E293B))
            Row(modifier = Modifier.padding(vertical = 6.dp), verticalAlignment = Alignment.CenterVertically) {
                repeat(5) {
                    Icon(
                            Icons.Default.Star,
                            contentDescription = null,
                            tint = Color(0xFFFBBF24),
                            modifier = Modifier.size(14.dp)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text("Mulai quiz/eas", fontSize = 12.sp, color = Color(0xFF64748B), fontWeight = FontWeight.Medium)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                    onClick = {},
                    colors =
                            ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF496E96),
                                    contentColor = Color.White
                            ), // IELS Blue
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(vertical = 12.dp)
            ) { Text("Mulai Ujian", color = Color.White, fontWeight = FontWeight.Bold) }
        }
    }
}


@Composable
fun SummaryBadge(text: String, baseColor: Color) {
    Surface(
            color = baseColor.copy(alpha = 0.08f),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.size(80.dp, 90.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, baseColor.copy(alpha = 0.15f))
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                    text,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = baseColor,
                    lineHeight = 20.sp
            )
        }
    }
}

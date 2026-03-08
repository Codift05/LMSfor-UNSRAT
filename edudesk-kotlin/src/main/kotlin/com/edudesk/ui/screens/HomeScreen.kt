package com.edudesk.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edudesk.ui.components.CourseCard
import com.edudesk.ui.components.HeroBanner
import com.edudesk.ui.components.TopNavigationBar

@Composable
fun HomeScreen() {
    Scaffold(
        topBar = { TopNavigationBar() }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFF8FAFC)) // Slate 50
                .verticalScroll(rememberScrollState())
        ) {
            HeroBanner(userName = "Miftahuddin")
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                Text(
                    "Pendaftaran Kelas",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E293B)
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Horizontal Row of Courses
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    CourseCard(
                        title = "Matematika Tingkat Lanjut",
                        instructor = "Novsalam Siaoun",
                        rating = 5f,
                        reviewsCount = 244,
                        price = "Rp9,000",
                        originalPrice = "Rp49,000",
                        modifier = Modifier.weight(1f)
                    )
                    CourseCard(
                        title = "Matematika Tingkat Lanjut",
                        instructor = "Novsalam Siaoun",
                        rating = 5f,
                        reviewsCount = 299,
                        price = "Rp9,000",
                        modifier = Modifier.weight(1f)
                    )
                    CourseCard(
                        title = "Matematika Tingkat Lanjut",
                        instructor = "Sielkosh ai Al",
                        rating = 5f,
                        reviewsCount = 398,
                        price = "Rp9,000",
                        isPremium = true,
                        modifier = Modifier.weight(1f)
                    )
                    CourseCard(
                        title = "Python Python Projects",
                        instructor = "Kialimatari Sonin",
                        rating = 5f,
                        reviewsCount = 396,
                        price = "Rp9,000",
                        modifier = Modifier.weight(1f)
                    )
                }
                
                Spacer(modifier = Modifier.height(32.dp))
                
                // Two Column Layout
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(32.dp)) {
                    // Left Column
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Mata Kuliah Saya", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // My Courses List (Vertical CourseCards with progress)
                        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            // Helper to render wide horizontal course cards
                            MyCourseListItem("Matematika Tingkat Lanjut", 0.8f)
                            MyCourseListItem("Matematika Tingkat Lanjut", 0.4f)
                            MyCourseListItem("Matematika Tingkat Lanjut", 0.2f)
                            MyCourseListItem("Matematika Tiruman Ung Data Senshat", 0.6f)
                        }
                        
                        Spacer(modifier = Modifier.height(32.dp))
                        
                        Text("Tugas Selesai & Tertunda", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // Task Accordion / Dropdowns
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            shape = RoundedCornerShape(12.dp),
                            elevation = CardDefaults.cardElevation(2.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text("Status", fontWeight = FontWeight.SemiBold)
                                    Button(
                                        onClick = {},
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B5CF6)),
                                        shape = RoundedCornerShape(8.dp),
                                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                                    ) {
                                        Text("Upload")
                                    }
                                }
                                
                                Spacer(modifier = Modifier.height(16.dp))
                                
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
                        Text("Ujian Mandatang", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // Upcoming Exams Cards
                        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                            ExamCard("Kerjakan Ujian/Kuis")
                            ExamCard("Kerjakan Ujian/Kuis\nQuizze 2")
                            ExamCard("Kerjakan Ujian/Kuis\nQuizze 3")
                        }
                        
                        Spacer(modifier = Modifier.height(32.dp))
                        
                        Text("Rapor Saya", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // Report Card
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            shape = RoundedCornerShape(12.dp),
                            elevation = CardDefaults.cardElevation(2.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text("Summary", fontWeight = FontWeight.SemiBold)
                                Spacer(modifier = Modifier.height(12.dp))
                                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                                    SummaryBadge("Grade\n6+")
                                    SummaryBadge("Grade\n8+")
                                    SummaryBadge("Grade\n7+")
                                }
                                
                                Spacer(modifier = Modifier.height(24.dp))
                                Text("Performance", fontWeight = FontWeight.SemiBold)
                                Spacer(modifier = Modifier.height(8.dp))
                                
                                // Placeholder for Chart
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(150.dp)
                                        .background(Color(0xFFF1F5F9), RoundedCornerShape(8.dp)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("Line Chart Canvas Placeholder", color = Color.Gray)
                                }
                            }
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(48.dp))
            }
        }
    }
}

// Subcomponents specifically for the Home Screen
@Composable
fun MyCourseListItem(title: String, progress: Float) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(1.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(60.dp, 40.dp).background(Color(0xFFE2E8F0), RoundedCornerShape(4.dp))
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Row {
                    repeat(5) {
                        Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFBBF24), modifier = Modifier.size(12.dp))
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                LinearProgressIndicator(
                    progress = progress,
                    modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp)),
                    color = Color(0xFF8B5CF6),
                    trackColor = Color(0xFFE2E8F0)
                )
            }
        }
    }
}

@Composable
fun TaskStatusDropdown(text: String) {
    Surface(
        color = Color(0xFFF8FAFC),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text, fontSize = 14.sp, fontWeight = FontWeight.Medium)
            Icon(Icons.Default.KeyboardArrowDown, contentDescription = null, tint = Color.Gray)
        }
    }
}

@Composable
fun ExamCard(title: String) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, fontSize = 16.sp, fontWeight = FontWeight.Bold, lineHeight = 22.sp)
            Row(modifier = Modifier.padding(vertical = 4.dp)) {
                repeat(5) {
                    Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFBBF24), modifier = Modifier.size(12.dp))
                }
            }
            Text("Mulai quiz/eas", fontSize = 12.sp, color = Color.Gray)
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B5CF6)),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Mulai Ujian", color = Color.White, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

@Composable
fun SummaryBadge(text: String) {
    Surface(
        color = Color(0xFFF1F5F9), // Slate 100
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.size(70.dp, 80.dp)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }
    }
}

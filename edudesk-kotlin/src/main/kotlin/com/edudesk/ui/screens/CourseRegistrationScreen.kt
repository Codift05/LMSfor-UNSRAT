package com.edudesk.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edudesk.ui.components.CourseCard
import com.edudesk.ui.components.TopNavigationBar
import com.edudesk.ui.navigation.NavController
import com.edudesk.ui.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseRegistrationScreen() {
    var searchQuery by remember { mutableStateOf("") }

    val allCourses = listOf(
        CourseData("Pemrograman Berorientasi Objek", "Dr. Eng. Rizal, S.T., M.T.", 244),
        CourseData("Struktur Data", "Prof. Dr. Ir. Budi, M.Sc.", 299),
        CourseData("Kecerdasan Buatan", "Prof. Dr. Ir. Budi, M.Sc.", 398, true),
        CourseData("Pengembangan Aplikasi Web", "Ir. Maria, S.Kom., M.Kom.", 396),
        CourseData("Jaringan Komputer", "Alexander S. M. Lumenta, S.T., M.T.", 150),
        CourseData("Sistem Operasi", "Dr. Eng. Rizal, S.T., M.T.", 180),
        CourseData("Basis Data", "Prof. Dr. Ir. Budi, M.Sc.", 210),
        CourseData("Etika Profesi", "Ir. Maria, S.Kom., M.Kom.", 90)
    )

    val filteredCourses = allCourses.filter {
        it.title.contains(searchQuery, ignoreCase = true) || 
        it.instructor.contains(searchQuery, ignoreCase = true)
    }

    Scaffold(
        topBar = { TopNavigationBar() }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFF8FAFC))
                .padding(horizontal = 48.dp, vertical = 24.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { NavController.navigateTo(Screen.Home) }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
                Text(
                    "Pendaftaran Kelas",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E293B)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
                placeholder = { Text("Cari mata kuliah atau dosen...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = Color(0xFF496E96),
                    unfocusedBorderColor = Color(0xFFE2E8F0)
                ),
                shape = MaterialTheme.shapes.medium
            )

            Spacer(modifier = Modifier.height(24.dp))

            if (filteredCourses.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Tidak ada mata kuliah yang ditemukan", color = Color.Gray)
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 280.dp),
                    horizontalArrangement = Arrangement.spacedBy(24.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(filteredCourses) { course ->
                        CourseCard(
                            title = course.title,
                            instructor = course.instructor,
                            reviewsCount = course.reviewsCount,
                            isPremium = course.isPremium,
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                // TODO: Navigate to CourseDetail with ID
                                NavController.navigateTo(Screen.CourseDetail)
                            }
                        )
                    }
                }
            }
        }
    }
}

data class CourseData(
    val title: String,
    val instructor: String,
    val reviewsCount: Int,
    val isPremium: Boolean = false
)

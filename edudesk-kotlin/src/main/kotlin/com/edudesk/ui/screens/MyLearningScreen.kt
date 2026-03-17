package com.edudesk.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edudesk.ui.components.CourseCard
import com.edudesk.ui.components.TopNavigationBar

@Composable
fun MyLearningScreen() {
    Scaffold(
        topBar = { TopNavigationBar() }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color.White)
                .padding(horizontal = 48.dp)
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            
            Text(
                "My Learning",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Sinkronisasi dengan daftar kelas
            // Import CourseData dan allCourses dari CourseRegistrationScreen
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

            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 220.dp),
                contentPadding = PaddingValues(bottom = 32.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                items(allCourses) { course ->
                    CourseCard(
                        title = course.title,
                        instructor = course.instructor,
                        progress = 0.0f, // Progress dummy, bisa di-update sesuai data
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

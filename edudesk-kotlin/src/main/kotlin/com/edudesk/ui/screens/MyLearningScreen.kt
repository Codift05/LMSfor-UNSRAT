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
            
            // Grid of Enrolled Courses
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 220.dp),
                contentPadding = PaddingValues(bottom = 32.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // Mock data for demonstration
                val enrolledCourses = listOf(
                    "Artificial Intelligence (AI) for Beginners" to 0.45f,
                    "Advanced Kotlin Development" to 0.10f,
                    "Modern Desktop UI Design with Compose" to 0.85f,
                    "SQLite & Exposed ORM Masterclass" to 0.0f,
                    "Data Structure & Algorithm" to 0.25f,
                    "Web Development Bootcamp" to 0.60f
                )
                
                items(enrolledCourses) { (title, progress) ->
                    CourseCard(
                        title = title,
                        instructor = "EduDesk Academy",
                        progress = progress,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

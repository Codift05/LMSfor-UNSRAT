package com.edudesk.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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
                .background(Color.White)
                .verticalScroll(rememberScrollState())
        ) {
            HeroBanner(userName = "Miftahuddin")
            
            Spacer(modifier = Modifier.height(48.dp))
            
            Column(modifier = Modifier.padding(horizontal = 48.dp)) {
                Text(
                    "What to learn next",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Text(
                    "Our top courses for you",
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Horizontal Row of Courses
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    CourseCard(
                        title = "Artificial Intelligence (AI) for Beginners",
                        instructor = "Google AI Studio",
                        progress = 0.45f
                    )
                    CourseCard(
                        title = "Advanced Kotlin Development",
                        instructor = "JetBrains Academy"
                    )
                    CourseCard(
                        title = "Modern Desktop UI Design with Compose",
                        instructor = "EduDesk Design Team"
                    )
                    CourseCard(
                        title = "SQLite & Exposed ORM Masterclass",
                        instructor = "Database Experts"
                    )
                }
                
                Spacer(modifier = Modifier.height(48.dp))
            }
        }
    }
}

package com.edudesk.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HeroBanner(userName: String) {
    Card(
        modifier = Modifier.fillMaxWidth().height(240.dp),
        shape = RoundedCornerShape(0.dp), // Udemy banners usually edge-to-edge
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Placeholder for background image
            Box(modifier = Modifier.fillMaxSize().background(Color(0xFFF7F9FA)))
            
            Row(
                modifier = Modifier.fillMaxSize().padding(48.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(360.dp)
                        .background(Color.White)
                        .padding(24.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Column {
                        Text(
                            "Welcome back, $userName", 
                            fontSize = 32.sp, 
                            fontWeight = FontWeight.Bold,
                            lineHeight = 40.sp
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            "Learning that gets you. Skills for your present (and your future). Get started with us.",
                            fontSize = 16.sp,
                            color = Color(0xFF6A6F73)
                        )
                    }
                }
                
                Spacer(modifier = Modifier.weight(1f))
                
                // Here would be the image of the person sitting on the chair from Udemy's UI
                // For now, we'll use a stylized BOX as a placeholder
                Box(
                    modifier = Modifier.fillMaxHeight().width(200.dp).background(Color(0xFFE1F5FE)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Banner Image", color = Color(0xFF0288D1))
                }
            }
        }
    }
}

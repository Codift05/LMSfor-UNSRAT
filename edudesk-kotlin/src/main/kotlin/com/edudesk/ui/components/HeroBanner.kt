package com.edudesk.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HeroBanner(userName: String, rolePrefix: String = "Mahasiswa") {
    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 24.dp)
    ) {
        Text(
            "Selamat Datang Kembali,\n$rolePrefix $userName",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 40.sp,
            color = Color(0xFF1E293B) // Dark Slate Gray
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Banner card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(Color(0xFF2B3A4A), Color(0xFF3B82F6), Color(0xFF4ADE80))
                    )
                )
        ) {
            Row(
                modifier = Modifier.fillMaxSize().padding(48.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        "Program Studi\nTeknik Informatika",
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        lineHeight = 44.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "Fakultas Teknik,\nUniversitas Sam Ratulangi",
                        fontSize = 16.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B5CF6)), // Purpleish
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp)
                    ) {
                        Text("Get started", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
                
                // Placeholder for person image
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(260.dp)
                        .background(Color.Transparent),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    // Empty for now, would hold an Image()
                }
            }
        }
    }
}

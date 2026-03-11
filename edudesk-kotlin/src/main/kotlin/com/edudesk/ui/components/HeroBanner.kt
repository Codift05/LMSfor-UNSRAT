package com.edudesk.ui.components

import androidx.compose.foundation.Image
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
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
        
        // Banner card using the new image
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(5f)
                .clip(RoundedCornerShape(16.dp))
        ) {
            Image(
                painter = painterResource("Banner Web IELS 2.png"),
                contentDescription = "IELS Banner",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            
            // Overlaying branding text
            Column(
                modifier = Modifier.fillMaxSize().padding(horizontal = 64.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    "Universitas Sam Ratulangi",
                    color = Color.White,
                    fontSize = 42.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "Fakultas Teknik",
                    color = Color.White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    "Program Studi Teknik Informatika",
                    color = Color.White,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

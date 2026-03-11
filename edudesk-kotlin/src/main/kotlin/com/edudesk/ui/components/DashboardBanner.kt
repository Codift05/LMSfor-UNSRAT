package com.edudesk.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edudesk.ui.theme.UdemyPurple
import com.edudesk.ui.theme.TextPrimary

@Composable
fun DashboardBanner(
    title: String,
    modifier: Modifier = Modifier
) {
    val name = title.replace("Selamat Datang Kembali,\n", "")
        .replace("Welcome back, ", "")
        .replace("\n", " ").trim()
    
    val initials = name.split(" ")
        .filter { it.isNotEmpty() }
        .take(2)
        .map { it.first().uppercaseChar() }
        .joinToString("")

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            // Circle Avatar
            Surface(
                modifier = Modifier.size(64.dp),
                shape = CircleShape,
                color = Color(0xFF1C1D1F) // Udemy dark avatar background
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        initials,
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column {
                Text(
                    text = "Welcome back, $name",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Text(
                    text = "Add occupation and interests",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = UdemyPurple,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.clickable { /* TODO */ }
                )
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Image(
            painter = painterResource("Banner Web IELS 2.png"),
            contentDescription = "IELS Banner",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(5f)
                .clip(RoundedCornerShape(0.dp)), // Udemy banners are usually flat
            contentScale = ContentScale.FillWidth
        )
    }
}

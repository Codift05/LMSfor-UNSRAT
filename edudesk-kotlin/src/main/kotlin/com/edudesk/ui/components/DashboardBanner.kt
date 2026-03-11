package com.edudesk.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DashboardBanner(
    title: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            title,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1E293B),
            lineHeight = 40.sp
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Image(
            painter = painterResource("Banner Web IELS 2.png"),
            contentDescription = "IELS Banner",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(5f)
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.FillWidth
        )
    }
}

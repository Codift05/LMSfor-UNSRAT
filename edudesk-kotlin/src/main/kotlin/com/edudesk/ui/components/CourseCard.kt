package com.edudesk.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseCard(
        title: String,
        instructor: String,
        rating: Float = 5.0f,
        reviewsCount: Int = 0,
        isPremium: Boolean = false,
        progress: Float? = null,
        modifier: Modifier = Modifier
) {
    Card(
            modifier = modifier.width(260.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            onClick = {}
    ) {
        Column {
            // Thumbnail Placeholder
            Box(
                    modifier =
                            Modifier.fillMaxWidth()
                                    .height(140.dp)
                                    .background(Color(0xFFE2E8F0)) // Slate 200
            )

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                        text = title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        lineHeight = 22.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Rating and Reviews
                Row(verticalAlignment = Alignment.CenterVertically) {
                    repeat(5) {
                        Icon(
                                Icons.Default.Star,
                                contentDescription = null,
                                tint = Color(0xFFFBBF24), // Amber 400
                                modifier = Modifier.size(14.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "($reviewsCount)", fontSize = 12.sp, color = Color.Gray)
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(text = instructor, fontSize = 12.sp, color = Color.Gray)

                Spacer(modifier = Modifier.height(8.dp))
                if (isPremium) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Surface(
                            color = Color(0xFF0033FF), // IELS Blue
                            shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                                "Premium",
                                color = Color.White,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }

                // For 'My Courses' view showing progress instead of price
                if (progress != null) {
                    Spacer(modifier = Modifier.height(12.dp))
                    LinearProgressIndicator(
                            progress = progress,
                            modifier =
                                    Modifier.fillMaxWidth()
                                            .height(6.dp)
                                            .clip(RoundedCornerShape(3.dp)),
                            color = Color(0xFFF000FF), // IELS Magenta
                            trackColor = Color(0xFFE2E8F0)
                    )
                }
            }
        }
    }
}

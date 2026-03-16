package com.edudesk.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
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
        modifier: Modifier = Modifier,
        onClick: () -> Unit = {}
) {
    val courseIcon: ImageVector = when {
        title.contains("Pemrograman", ignoreCase = true) -> Icons.Default.Code
        title.contains("Data", ignoreCase = true) -> Icons.Default.Storage
        title.contains("Kecerdasan", ignoreCase = true) || title.contains("AI", ignoreCase = true) -> Icons.Default.Psychology
        title.contains("Web", ignoreCase = true) -> Icons.Default.Language
        else -> Icons.Default.Book
    }

    Card(
            modifier = modifier.width(260.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
                disabledContainerColor = Color.White
            ),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
            border = BorderStroke(1.dp, Color(0xFFDDE3EA)),
            onClick = onClick
    ) {
        Column {
            // Thumbnail with Background Image and Centered Icon
            Box(
                    modifier =
                            Modifier.fillMaxWidth()
                                    .height(140.dp)
                                    .background(Color(0xFFF1F5F9)),
                    contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource("banner matkul 2.png"),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    alignment = BiasAlignment(0f, -0.7f)
                )

                // Semi-transparent overlay
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.1f))
                )

                Icon(
                    imageVector = courseIcon,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(48.dp)
                )
            }

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
                // For 'My Courses' view showing progress instead of price
                if (progress != null) {

                    Spacer(modifier = Modifier.height(12.dp))
                    LinearProgressIndicator(
                            progress = progress,
                            modifier =
                                    Modifier.fillMaxWidth()
                                            .height(6.dp)
                                            .clip(RoundedCornerShape(3.dp)),
                            color = Color(0xFF496E96), // Theme Blue
                            trackColor = Color(0xFFE2E8F0)
                    )
                }
            }
        }
    }
}

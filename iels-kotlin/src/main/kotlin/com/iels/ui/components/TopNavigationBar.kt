package com.iels.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iels.services.SessionManager
import com.iels.ui.navigation.NavController
import com.iels.ui.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNavigationBar() {
    var profileMenuExpanded by remember { mutableStateOf(false) }
    val currentUser = SessionManager.currentUser

    Surface(
            modifier = Modifier.fillMaxWidth().height(72.dp),
            shadowElevation = 2.dp,
            color = Color.White
    ) {
        Row(
                modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp),
                verticalAlignment = Alignment.CenterVertically
        ) {
            // Logo
            TextButton(onClick = { NavController.navigateTo(Screen.Home) }) {
                Image(
                        painter = painterResource("IELS Logo 4.png"),
                        contentDescription = "Iels Logo",
                        modifier = Modifier.height(32.dp)
                )
            }

            Spacer(modifier = Modifier.width(32.dp))

            Spacer(modifier = Modifier.weight(1f))

            Spacer(modifier = Modifier.width(32.dp))

            // Navigation Links removed (Commercial elements not needed for University LMS)

            IconButton(onClick = {}) {
                BadgedBox(
                        badge = {
                            Box(
                                    modifier = Modifier
                                            .size(8.dp)
                                            .offset(x = (-4).dp, y = 4.dp)
                                            .background(Color(0xFFE53935), CircleShape)
                            )
                        }
                ) {
                    Icon(
                            Icons.Default.Notifications,
                            contentDescription = "Notifications",
                            tint = Color(0xFF4B5563) // Refined gray for better look
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Profile & Dropdown
            Box {
                Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable { profileMenuExpanded = !profileMenuExpanded }
                ) {
                    Surface(
                            modifier = Modifier.size(36.dp),
                            shape = CircleShape,
                            color = Color(0xFF1C1D1F)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                    currentUser?.name?.take(2)?.uppercase() ?: "US",
                                    color = Color.White,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Icon(
                            Icons.Default.KeyboardArrowDown,
                            contentDescription = null,
                            tint = Color(0xFF2D2F31)
                    )
                }

                MaterialTheme(
                    colorScheme = MaterialTheme.colorScheme.copy(
                        surface = Color.White,
                        surfaceVariant = Color.White
                    )
                ) {
                    DropdownMenu(
                        expanded = profileMenuExpanded,
                        onDismissRequest = { profileMenuExpanded = false },
                        modifier = Modifier.width(260.dp).background(Color.White)
                    ) {
                        // Header Section
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 16.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Surface(
                                    modifier = Modifier.size(56.dp),
                                    shape = CircleShape,
                                    color = Color(0xFF1C1D1F)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text(
                                            currentUser?.name?.split(" ")?.filter { it.isNotEmpty() }?.take(2)?.map { it.first().uppercaseChar() }?.joinToString("") ?: "US",
                                            color = Color.White,
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.width(16.dp))
                                Column {
                                    Text(
                                        currentUser?.name ?: "User",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                        color = Color(0xFF1C1D1F)
                                    )
                                    Text(
                                        currentUser?.email ?: (currentUser?.nim ?: ""),
                                        fontSize = 12.sp,
                                        color = Color(0xFF6A6F73)
                                    )
                                }
                            }
                        }

                        HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp), thickness = 0.5.dp, color = Color(0xFFD1D7DC))


                        DropdownMenuItem(
                            text = { Text("Keluar", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFFB3261E)) },
                            onClick = {
                                profileMenuExpanded = false
                                SessionManager.currentUser = null
                                NavController.navigateTo(Screen.Login)
                            }
                        )
                    }
                }
            }
        }
    }
}

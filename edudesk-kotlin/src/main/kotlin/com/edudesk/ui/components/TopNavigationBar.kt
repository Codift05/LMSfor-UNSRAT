package com.edudesk.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edudesk.services.SessionManager
import com.edudesk.ui.navigation.NavController
import com.edudesk.ui.navigation.Screen

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
                        painter = painterResource("IELS Logo 2.png"),
                        contentDescription = "EduDesk Logo",
                        modifier = Modifier.height(32.dp)
                )
            }

            Spacer(modifier = Modifier.width(32.dp))

            // Search Bar
            OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    placeholder = { Text("Cari kursus...", fontSize = 14.sp, color = Color.Gray) },
                    leadingIcon = {
                        Icon(Icons.Default.Search, contentDescription = null, tint = Color.Gray)
                    },
                    modifier = Modifier.weight(1f).height(48.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors =
                            TextFieldDefaults.outlinedTextFieldColors(
                                    unfocusedBorderColor = Color.Transparent,
                                    focusedBorderColor = Color.Transparent,
                                    containerColor = Color(0xFFF3F4F6)
                            )
            )

            Spacer(modifier = Modifier.width(32.dp))

            // Navigation Links
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable {}) {
                Text("Eksplorasi", color = Color(0xFF2D2F31), fontWeight = FontWeight.Medium)
                Icon(
                        Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                        tint = Color(0xFF2D2F31)
                )
            }

            Spacer(modifier = Modifier.width(24.dp))

            TextButton(onClick = {}) {
                Text("Berlangganan", color = Color(0xFF2D2F31), fontWeight = FontWeight.Medium)
            }

            Spacer(modifier = Modifier.width(16.dp))

            IconButton(onClick = {}) {
                BadgedBox(badge = { Badge { Text("") } }) {
                    Icon(
                            Icons.Default.Notifications,
                            contentDescription = "Notifications",
                            tint = Color(0xFF2D2F31)
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
                            color = Color(0xFF374151)
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

                DropdownMenu(
                        expanded = profileMenuExpanded,
                        onDismissRequest = { profileMenuExpanded = false }
                ) {
                    DropdownMenuItem(
                            text = {
                                Column {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Surface(
                                                modifier = Modifier.size(36.dp),
                                                shape = CircleShape,
                                                color = Color(0xFF374151)
                                        ) {
                                            Box(contentAlignment = Alignment.Center) {
                                                Text(
                                                        currentUser?.name?.take(2)?.uppercase()
                                                                ?: "US",
                                                        color = Color.White,
                                                        fontSize = 14.sp,
                                                        fontWeight = FontWeight.Bold
                                                )
                                            }
                                        }
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Column {
                                            Text(
                                                    currentUser?.name ?: "User",
                                                    fontWeight = FontWeight.Bold
                                            )
                                            Text(
                                                    currentUser?.role ?: "Student",
                                                    fontSize = 12.sp,
                                                    color = Color.Gray
                                            )
                                        }
                                    }
                                }
                            },
                            onClick = { profileMenuExpanded = false }
                    )
                    Divider()
                    DropdownMenuItem(
                            text = { Text("Edit Profile") },
                            onClick = {
                                profileMenuExpanded = false
                                NavController.navigateTo(Screen.Profile)
                            }
                    )
                    Divider()
                    DropdownMenuItem(
                            text = { Text("Logout") },
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

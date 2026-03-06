package com.edudesk.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edudesk.ui.navigation.NavController
import com.edudesk.ui.navigation.Screen
import com.edudesk.ui.theme.UdemyPurple

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNavigationBar() {
    Surface(
        modifier = Modifier.fillMaxWidth().height(72.dp),
        shadowElevation = 2.dp,
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Logo
            TextButton(onClick = { NavController.navigateTo(Screen.Home) }) {
                Text(
                    "EduDesk",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = UdemyPurple
                )
            }
            
            Spacer(modifier = Modifier.width(32.dp))
            
            // Search Bar
            OutlinedTextField(
                value = "",
                onValueChange = {},
                placeholder = { Text("Search for anything", fontSize = 14.sp) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                modifier = Modifier.weight(1f).height(48.dp),
                shape = RoundedCornerShape(24.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = Color(0xFFD1D7DC),
                    containerColor = Color(0xFFF7F9FA)
                )
            )
            
            Spacer(modifier = Modifier.width(32.dp))
            
            // Navigation Links
            TextButton(onClick = { NavController.navigateTo(Screen.MyLearning) }) {
                Text("My Learning", color = Color(0xFF2D2F31))
            }
            TextButton(onClick = { NavController.navigateTo(Screen.Assignments) }) {
                Text("Assignments", color = Color(0xFF2D2F31))
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            IconButton(onClick = {}) {
                Icon(Icons.Default.Notifications, contentDescription = null)
            }
            
            Surface(
                modifier = Modifier.size(36.dp),
                shape = RoundedCornerShape(18.dp),
                color = Color(0xFF2D2F31)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text("MU", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

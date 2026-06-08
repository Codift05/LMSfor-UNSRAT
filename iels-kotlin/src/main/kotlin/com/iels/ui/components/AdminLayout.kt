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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iels.services.SessionManager
import com.iels.ui.navigation.NavController
import com.iels.ui.navigation.Screen

@Composable
fun AdminLayout(activeMenu: String, content: @Composable () -> Unit) {
    Row(modifier = Modifier.fillMaxSize().background(Color.White)) {
        // --- LEFT SIDEBAR ---
        Column(
            modifier = Modifier
                .width(260.dp)
                .fillMaxHeight()
                .background(Color(0xFFF8FAFC))
                .padding(24.dp)
        ) {
            // Logo Area
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource("IELS Logo 3.png"),
                    contentDescription = "Iels Logo",
                    modifier = Modifier.width(140.dp)
                )
            }

            Spacer(modifier = Modifier.height(48.dp))

            // Navigation Items
            val navItems = listOf(
                Pair("Dashboard", Icons.Default.Dashboard) to Screen.AdminOverview,
                Pair("Pembuat Ujian", Icons.Default.Build) to Screen.AdminDashboard,
                Pair("Monitoring", Icons.Default.Visibility) to Screen.AdminMonitoring,
                Pair("Hasil Ujian", Icons.Default.Assessment) to Screen.AdminResults,
                Pair("Kelola Pengguna", Icons.Default.People) to Screen.AdminUsers,
                Pair("Pengaturan", Icons.Default.Settings) to Screen.AdminSettings
            )

            navItems.forEach { (item, screen) ->
                val isActive = item.first == activeMenu
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (isActive) Color(0xFF496E96) else Color.Transparent)
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                        .clickable { NavController.navigateTo(screen) }
                ) {
                    Icon(
                        imageVector = item.second,
                        contentDescription = item.first,
                        tint = if (isActive) Color.White else Color(0xFF64748B),
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = item.first,
                        color = if (isActive) Color.White else Color(0xFF64748B),
                        fontWeight = if (isActive) FontWeight.Bold else FontWeight.Medium,
                        fontSize = 15.sp
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            Spacer(modifier = Modifier.weight(1f))
            HorizontalDivider(color = Color(0xFFE2E8F0))
            Spacer(modifier = Modifier.height(24.dp))

            // User Profile
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    shape = CircleShape,
                    color = Color(0xFFE2E8F0),
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(Icons.Default.Person, contentDescription = null, tint = Color(0xFF64748B), modifier = Modifier.padding(8.dp))
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(SessionManager.currentUser?.name ?: "Admin User", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A), maxLines = 1)
                    Text("Administrator", fontSize = 12.sp, color = Color(0xFF64748B))
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { 
                    SessionManager.logout()
                    NavController.navigateTo(Screen.Login) 
                }
            ) {
                Icon(Icons.Default.ExitToApp, contentDescription = null, tint = Color(0xFF64748B), modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Keluar", fontSize = 14.sp, color = Color(0xFF64748B), fontWeight = FontWeight.Medium)
            }
        }

        // --- MAIN CONTENT (INJECTED) ---
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(Color.White)
        ) {
            content()
        }
    }
}

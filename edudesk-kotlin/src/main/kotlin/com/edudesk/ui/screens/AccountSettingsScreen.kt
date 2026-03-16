package com.edudesk.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edudesk.services.SessionManager
import com.edudesk.ui.components.TopNavigationBar
import com.edudesk.ui.navigation.NavController
import com.edudesk.ui.navigation.Screen

@Composable
fun AccountSettingsScreen() {
    var notifEmail by remember { mutableStateOf(true) }
    var notifTugas by remember { mutableStateOf(true) }
    var notifUjian by remember { mutableStateOf(true) }
    var notifPengumuman by remember { mutableStateOf(false) }
    var twoFactor by remember { mutableStateOf(false) }

    val currentUser = SessionManager.currentUser

    Scaffold(topBar = { TopNavigationBar() }) { padding ->
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFE2E8EF))
        ) {
            // LEFT SIDEBAR: Navigation
            Column(
                modifier = Modifier
                    .width(260.dp)
                    .fillMaxHeight()
                    .background(Color.White)
                    .padding(vertical = 24.dp)
            ) {
                Text("Pengaturan", fontSize = 20.sp, fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E293B), modifier = Modifier.padding(horizontal = 24.dp))
                Spacer(modifier = Modifier.height(24.dp))

                SettingsSidebarItem(Icons.Default.Person, "Info Akun", selected = true)
                SettingsSidebarItem(Icons.Default.Lock, "Keamanan", selected = false)
                SettingsSidebarItem(Icons.Default.Notifications, "Notifikasi", selected = false)
                SettingsSidebarItem(Icons.Default.PrivacyTip, "Privasi", selected = false)
                SettingsSidebarItem(Icons.Default.Language, "Bahasa & Wilayah", selected = false)
                SettingsSidebarItem(Icons.Default.Palette, "Tampilan", selected = false)

                Spacer(modifier = Modifier.weight(1f))
                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                Spacer(modifier = Modifier.height(12.dp))
                SettingsSidebarItem(Icons.Default.Logout, "Keluar", selected = false, isDestructive = true,
                    onClick = {
                        SessionManager.currentUser = null
                        NavController.navigateTo(Screen.Login)
                    })
            }

            // Thin divider
            Spacer(modifier = Modifier.width(1.dp).fillMaxHeight().background(Color(0xFFDDE3EA)))

            // RIGHT CONTENT
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .verticalScroll(rememberScrollState())
                    .padding(40.dp)
            ) {
                Text("Info Akun", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
                Text("Kelola informasi akun dan preferensi kamu.", fontSize = 14.sp, color = Color(0xFF64748B))

                Spacer(modifier = Modifier.height(32.dp))

                // Avatar Section
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(0.dp),
                    border = BorderStroke(1.dp, Color(0xFFDDE3EA)),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(24.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(modifier = Modifier.size(72.dp), shape = CircleShape,
                            color = Color(0xFF496E96)) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    currentUser?.name?.split(" ")?.filter { it.isNotEmpty() }
                                        ?.take(2)?.map { it.first().uppercaseChar() }?.joinToString("") ?: "US",
                                    color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(20.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(currentUser?.name ?: "User", fontWeight = FontWeight.Bold,
                                fontSize = 18.sp, color = Color(0xFF1E293B))
                            Text(currentUser?.email ?: currentUser?.nim ?: "",
                                fontSize = 14.sp, color = Color(0xFF64748B))
                            Text(when (currentUser?.role) {
                                "student" -> "Mahasiswa"
                                "instructor" -> "Dosen"
                                "admin" -> "Administrator"
                                else -> "Pengguna"
                            }, fontSize = 12.sp, color = Color(0xFF496E96), fontWeight = FontWeight.SemiBold)
                        }
                        OutlinedButton(
                            onClick = { NavController.navigateTo(Screen.Profile) },
                            shape = RoundedCornerShape(10.dp),
                            border = BorderStroke(1.dp, Color(0xFF496E96)),
                            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp)
                        ) {
                            Text("Edit Profil", color = Color(0xFF496E96), fontWeight = FontWeight.SemiBold)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Info Fields
                SettingsSectionCard(title = "Informasi Pribadi") {
                    SettingsField("Nama Lengkap", currentUser?.name ?: "")
                    SettingsField("NIM / Email", currentUser?.nim ?: currentUser?.email ?: "")
                    SettingsField("Program Studi", "Teknik Informatika")
                    SettingsField("Semester", "6")
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Security
                SettingsSectionCard(title = "Keamanan") {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Autentikasi Dua Faktor", fontWeight = FontWeight.SemiBold,
                                fontSize = 14.sp, color = Color(0xFF1E293B))
                            Text("Tambah lapisan keamanan ekstra pada akun",
                                fontSize = 12.sp, color = Color(0xFF64748B))
                        }
                        Switch(
                            checked = twoFactor,
                            onCheckedChange = { twoFactor = it },
                            colors = SwitchDefaults.colors(checkedThumbColor = Color.White,
                                checkedTrackColor = Color(0xFF496E96))
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedButton(
                        onClick = {},
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, Color(0xFFDDE3EA)),
                        contentPadding = PaddingValues(vertical = 12.dp)
                    ) {
                        Icon(Icons.Default.Lock, contentDescription = null,
                            tint = Color(0xFF64748B), modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Ubah Kata Sandi", color = Color(0xFF334155))
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Notifications
                SettingsSectionCard(title = "Notifikasi") {
                    NotifToggleRow("Notifikasi via Email", "Kirim pemberitahuan ke email", notifEmail) { notifEmail = it }
                    Spacer(modifier = Modifier.height(12.dp))
                    NotifToggleRow("Tugas Baru", "Ingatkan ketika ada tugas baru ditambahkan", notifTugas) { notifTugas = it }
                    Spacer(modifier = Modifier.height(12.dp))
                    NotifToggleRow("Pengingat Ujian", "Notifikasi H-1 sebelum ujian", notifUjian) { notifUjian = it }
                    Spacer(modifier = Modifier.height(12.dp))
                    NotifToggleRow("Pengumuman Kampus", "Berita dan pengumuman dari universitas", notifPengumuman) { notifPengumuman = it }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Danger Zone
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF5F5)),
                    elevation = CardDefaults.cardElevation(0.dp),
                    border = BorderStroke(1.dp, Color(0xFFFFCDD2)),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        Text("Zona Berbahaya", fontWeight = FontWeight.Bold,
                            fontSize = 16.sp, color = Color(0xFFB71C1C))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Tindakan di bawah ini tidak dapat dibatalkan.",
                            fontSize = 13.sp, color = Color(0xFFE53935))
                        Spacer(modifier = Modifier.height(16.dp))
                        OutlinedButton(
                            onClick = {},
                            shape = RoundedCornerShape(10.dp),
                            border = BorderStroke(1.dp, Color(0xFFE53935)),
                            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp)
                        ) {
                            Text("Nonaktifkan Akun", color = Color(0xFFE53935), fontWeight = FontWeight.SemiBold)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}

@Composable
fun SettingsSidebarItem(
    icon: ImageVector,
    label: String,
    selected: Boolean,
    isDestructive: Boolean = false,
    onClick: () -> Unit = {}
) {
    val textColor = when {
        isDestructive -> Color(0xFFE53935)
        selected -> Color(0xFF496E96)
        else -> Color(0xFF475569)
    }
    Surface(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 2.dp),
        color = if (selected) Color(0xFFEFF4FB) else Color.Transparent,
        shape = RoundedCornerShape(10.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = null, tint = textColor, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(12.dp))
            Text(label, fontSize = 14.sp, fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
                color = textColor)
        }
    }
}

@Composable
fun SettingsSectionCard(title: String, content: @Composable ColumnScope.() -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(0.dp),
        border = BorderStroke(1.dp, Color(0xFFDDE3EA)),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text(title, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
            Spacer(modifier = Modifier.height(20.dp))
            content()
        }
    }
}

@Composable
fun SettingsField(label: String, value: String) {
    Column(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)) {
        Text(label, fontSize = 12.sp, color = Color(0xFF64748B), fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = value,
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF496E96),
                unfocusedBorderColor = Color(0xFFE2E8F0),
                unfocusedContainerColor = Color(0xFFFAFAFA)
            ),
            singleLine = true
        )
    }
}

@Composable
fun NotifToggleRow(title: String, subtitle: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(title, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = Color(0xFF1E293B))
            Text(subtitle, fontSize = 12.sp, color = Color(0xFF64748B))
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = Color(0xFF496E96),
                uncheckedBorderColor = Color(0xFFE2E8F0)
            )
        )
    }
}

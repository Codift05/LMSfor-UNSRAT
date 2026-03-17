package com.edudesk.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.automirrored.filled.Logout
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

// ── Local design tokens ─────────────────────────────────────────────────────
private val AppBlue     = Color(0xFF496E96)
private val TextDark    = Color(0xFF1E293B)
private val TextMuted   = Color(0xFF64748B)
private val BorderColor = Color(0xFFDDE3EA)
private val BgPage      = Color(0xFFE2E8EF)
private val BgSelected  = Color(0xFFEFF4FB)

// ── Tab identifiers ─────────────────────────────────────────────────────────
enum class SettingsTab { InfoAkun, Keamanan, Notifikasi, Privasi, BahasaWilayah, Tampilan }

@Composable
fun AccountSettingsScreen() {
    var selectedTab by remember { mutableStateOf(SettingsTab.InfoAkun) }

    // Notification toggles
    var notifEmail      by remember { mutableStateOf(true) }
    var notifTugas      by remember { mutableStateOf(true) }
    var notifUjian      by remember { mutableStateOf(true) }
    var notifPengumuman by remember { mutableStateOf(false) }
    // Security
    var twoFactor       by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    // Theme / display
    var darkMode        by remember { mutableStateOf(false) }
    var compactMode     by remember { mutableStateOf(false) }
    // Language
    var selectedLang    by remember { mutableStateOf("Indonesia") }

    val currentUser = SessionManager.currentUser

    val initials = currentUser?.name
        ?.split(" ")?.filter { it.isNotEmpty() }?.take(2)
        ?.map { it.first().uppercaseChar() }?.joinToString("") ?: "US"

    val roleLabel = when (currentUser?.role) {
        "student"    -> "Mahasiswa"
        "instructor" -> "Dosen"
        "admin"      -> "Administrator"
        else         -> "Pengguna"
    }

    Scaffold(topBar = { TopNavigationBar() }) { padding ->
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(BgPage)
        ) {
            // ── LEFT SIDEBAR ─────────────────────────────────────────────────
            Column(
                modifier = Modifier
                    .width(260.dp)
                    .fillMaxHeight()
                    .background(Color.White)
                    .padding(vertical = 28.dp)
            ) {
                // Header
                Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                    Text("Pengaturan", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextDark)
                    Text("Kelola akun & preferensi kamu", fontSize = 12.sp, color = TextMuted)
                }

                Spacer(modifier = Modifier.height(20.dp))
                HorizontalDivider(color = BorderColor)
                Spacer(modifier = Modifier.height(12.dp))

                // Nav items — each changes selectedTab
                SettingsSidebarItem(
                    icon = Icons.Default.Person, label = "Info Akun",
                    selected = selectedTab == SettingsTab.InfoAkun,
                    onClick = { selectedTab = SettingsTab.InfoAkun }
                )
                SettingsSidebarItem(
                    icon = Icons.Default.Lock, label = "Keamanan",
                    selected = selectedTab == SettingsTab.Keamanan,
                    onClick = { selectedTab = SettingsTab.Keamanan }
                )
                SettingsSidebarItem(
                    icon = Icons.Default.Notifications, label = "Notifikasi",
                    selected = selectedTab == SettingsTab.Notifikasi,
                    onClick = { selectedTab = SettingsTab.Notifikasi }
                )
                SettingsSidebarItem(
                    icon = Icons.Default.PrivacyTip, label = "Privasi",
                    selected = selectedTab == SettingsTab.Privasi,
                    onClick = { selectedTab = SettingsTab.Privasi }
                )
                SettingsSidebarItem(
                    icon = Icons.Default.Language, label = "Bahasa & Wilayah",
                    selected = selectedTab == SettingsTab.BahasaWilayah,
                    onClick = { selectedTab = SettingsTab.BahasaWilayah }
                )
                SettingsSidebarItem(
                    icon = Icons.Default.Palette, label = "Tampilan",
                    selected = selectedTab == SettingsTab.Tampilan,
                    onClick = { selectedTab = SettingsTab.Tampilan }
                )

                Spacer(modifier = Modifier.weight(1f))

                // User mini-card
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .background(BgSelected, RoundedCornerShape(12.dp))
                        .padding(horizontal = 12.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(modifier = Modifier.size(36.dp), shape = CircleShape, color = AppBlue) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(initials, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(currentUser?.name ?: "User", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = TextDark)
                        Text(roleLabel, fontSize = 10.sp, color = AppBlue, fontWeight = FontWeight.Medium)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), color = BorderColor)
                Spacer(modifier = Modifier.height(8.dp))

                SettingsSidebarItem(
                    icon = Icons.AutoMirrored.Filled.Logout, label = "Keluar",
                    selected = false, isDestructive = true,
                    onClick = {
                        SessionManager.currentUser = null
                        NavController.navigateTo(Screen.Login)
                    }
                )
            }

            // Thin separator
            Box(modifier = Modifier.width(1.dp).fillMaxHeight().background(BorderColor))

            // ── RIGHT CONTENT — swap based on selectedTab ─────────────────────
            val scrollState = rememberScrollState()
            LaunchedEffect(selectedTab) { scrollState.animateScrollTo(0) }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .verticalScroll(scrollState)
                    .padding(horizontal = 48.dp, vertical = 36.dp)
            ) {
                when (selectedTab) {
                    SettingsTab.InfoAkun      -> TabInfoAkun(currentUser, initials, roleLabel)
                    SettingsTab.Keamanan      -> TabKeamanan(twoFactor, onTwoFactorChange = { twoFactor = it }, showDeleteDialog, onShowDeleteDialog = { showDeleteDialog = it })
                    SettingsTab.Notifikasi    -> TabNotifikasi(notifEmail, notifTugas, notifUjian, notifPengumuman,
                        onEmail = { notifEmail = it }, onTugas = { notifTugas = it },
                        onUjian = { notifUjian = it }, onPengumuman = { notifPengumuman = it })
                    SettingsTab.Privasi       -> TabPrivasi()
                    SettingsTab.BahasaWilayah -> TabBahasaWilayah(selectedLang, onLangChange = { selectedLang = it })
                    SettingsTab.Tampilan      -> TabTampilan(darkMode, compactMode,
                        onDarkMode = { darkMode = it }, onCompact = { compactMode = it })
                }
            }
        }
    }

    // Confirmation dialog for account deactivation
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            containerColor   = Color.White,
            shape            = RoundedCornerShape(16.dp),
            icon = {
                Box(
                    modifier = Modifier.size(52.dp).background(Color(0xFFFFF5F5), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Warning, contentDescription = null, tint = Color(0xFFE53935), modifier = Modifier.size(26.dp))
                }
            },
            title = { Text("Nonaktifkan Akun?", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = TextDark) },
            text = {
                Text(
                    "Akun kamu akan dinonaktifkan dan tidak dapat diakses. Hubungi admin jika ingin mengaktifkannya kembali.",
                    fontSize = 14.sp, color = TextMuted, lineHeight = 22.sp
                )
            },
            confirmButton = {
                Button(
                    onClick = { showDeleteDialog = false },
                    colors  = ButtonDefaults.buttonColors(containerColor = Color(0xFFE53935)),
                    shape   = RoundedCornerShape(10.dp)
                ) { Text("Nonaktifkan", fontWeight = FontWeight.SemiBold) }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = { showDeleteDialog = false },
                    shape   = RoundedCornerShape(10.dp),
                    border  = BorderStroke(1.dp, BorderColor)
                ) { Text("Batal", color = TextMuted) }
            }
        )
    }
}

// ════════════════════════════════════════════════════════════════════════════
// TABS
// ════════════════════════════════════════════════════════════════════════════

@Composable
private fun TabInfoAkun(currentUser: com.edudesk.models.User?, initials: String, roleLabel: String) {
    SettingsPageHeader("Info Akun", "Kelola informasi akun dan preferensi kamu.")
    Spacer(modifier = Modifier.height(28.dp))

    // Avatar card
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(0.dp),
        border = BorderStroke(1.dp, BorderColor), shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(modifier = Modifier.padding(24.dp), verticalAlignment = Alignment.CenterVertically) {
            Surface(modifier = Modifier.size(76.dp), shape = CircleShape, color = AppBlue) {
                Box(contentAlignment = Alignment.Center) {
                    Text(initials, color = Color.White, fontSize = 26.sp, fontWeight = FontWeight.Bold)
                }
            }
            Spacer(modifier = Modifier.width(20.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(currentUser?.name ?: "User", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = TextDark)
                Spacer(modifier = Modifier.height(2.dp))
                Text(currentUser?.email ?: currentUser?.nim ?: "", fontSize = 13.sp, color = TextMuted)
                Spacer(modifier = Modifier.height(6.dp))
                Surface(color = AppBlue.copy(alpha = 0.08f), shape = RoundedCornerShape(20.dp)) {
                    Text(roleLabel, modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp),
                        fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = AppBlue)
                }
            }
            Button(
                onClick = { NavController.navigateTo(Screen.Profile) },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AppBlue, contentColor = Color.White),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp)
            ) {
                Icon(Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(15.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Edit Profil", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
            }
        }
    }

    Spacer(modifier = Modifier.height(20.dp))

    SettingsSectionCard("Informasi Pribadi", Icons.Default.Person, Color(0xFFEFF4FB), AppBlue) {
        SettingsInfoRow("Nama Lengkap",  currentUser?.name ?: "")
        SettingsDivider()
        SettingsInfoRow("NIM / Email",   currentUser?.nim ?: currentUser?.email ?: "")
        SettingsDivider()
        SettingsInfoRow("Program Studi", "Teknik Informatika")
        SettingsDivider()
        SettingsInfoRow("Semester",      "6")
    }

    Spacer(modifier = Modifier.height(48.dp))
}

@Composable
private fun TabKeamanan(
    twoFactor: Boolean, onTwoFactorChange: (Boolean) -> Unit,
    showDeleteDialog: Boolean, onShowDeleteDialog: (Boolean) -> Unit
) {
    SettingsPageHeader("Keamanan", "Jaga keamanan akun kamu.")
    Spacer(modifier = Modifier.height(28.dp))

    SettingsSectionCard("Keamanan Akun", Icons.Default.Lock, Color(0xFFF0F4FF), Color(0xFF4B6FD4)) {
        // 2FA toggle
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(40.dp).background(Color(0xFFF0F4FF), RoundedCornerShape(10.dp)),
                    contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.Shield, contentDescription = null, tint = Color(0xFF4B6FD4), modifier = Modifier.size(20.dp))
                }
                Spacer(modifier = Modifier.width(14.dp))
                Column {
                    Text("Autentikasi Dua Faktor", fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = TextDark)
                    Text("Tambah lapisan keamanan ekstra pada akun", fontSize = 12.sp, color = TextMuted)
                }
            }
            Switch(checked = twoFactor, onCheckedChange = onTwoFactorChange,
                colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = AppBlue))
        }

        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider(color = Color(0xFFF1F5F9))
        Spacer(modifier = Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedButton(onClick = {}, modifier = Modifier.weight(1f), shape = RoundedCornerShape(10.dp),
                border = BorderStroke(1.dp, BorderColor), contentPadding = PaddingValues(vertical = 12.dp)) {
                Icon(Icons.Default.Lock, contentDescription = null, tint = TextMuted, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Ubah Kata Sandi", color = Color(0xFF334155), fontSize = 13.sp)
            }
            OutlinedButton(onClick = {}, modifier = Modifier.weight(1f), shape = RoundedCornerShape(10.dp),
                border = BorderStroke(1.dp, BorderColor), contentPadding = PaddingValues(vertical = 12.dp)) {
                Icon(Icons.Default.Devices, contentDescription = null, tint = TextMuted, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Sesi Aktif", color = Color(0xFF334155), fontSize = 13.sp)
            }
        }
    }

    Spacer(modifier = Modifier.height(20.dp))

    // Danger zone
    Card(colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(0.dp),
        border = BorderStroke(1.dp, Color(0xFFFFCDD2)), shape = RoundedCornerShape(16.dp), modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(24.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(36.dp).background(Color(0xFFFFF5F5), RoundedCornerShape(10.dp)),
                    contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.Warning, contentDescription = null, tint = Color(0xFFE53935), modifier = Modifier.size(18.dp))
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text("Zona Berbahaya", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color(0xFFB71C1C))
                    Text("Tindakan di bawah ini tidak dapat dibatalkan.", fontSize = 12.sp, color = Color(0xFFE53935))
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(color = Color(0xFFFFECEC))
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Nonaktifkan Akun", fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = TextDark)
                    Text("Akun tidak dapat diakses hingga diaktifkan kembali oleh admin.",
                        fontSize = 12.sp, color = TextMuted)
                }
                Spacer(modifier = Modifier.width(16.dp))
                OutlinedButton(onClick = { onShowDeleteDialog(true) }, shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.dp, Color(0xFFE53935)),
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp)) {
                    Text("Nonaktifkan", color = Color(0xFFE53935), fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                }
            }
        }
    }

    Spacer(modifier = Modifier.height(48.dp))
}

@Composable
private fun TabNotifikasi(
    notifEmail: Boolean, notifTugas: Boolean, notifUjian: Boolean, notifPengumuman: Boolean,
    onEmail: (Boolean) -> Unit, onTugas: (Boolean) -> Unit,
    onUjian: (Boolean) -> Unit, onPengumuman: (Boolean) -> Unit
) {
    SettingsPageHeader("Notifikasi", "Atur kapan dan bagaimana kamu menerima pemberitahuan.")
    Spacer(modifier = Modifier.height(28.dp))

    SettingsSectionCard("Preferensi Notifikasi", Icons.Default.Notifications, Color(0xFFFFF8EB), Color(0xFFD97706)) {
        NotifToggleRow(Icons.Default.Email,     Color(0xFFEFF4FB), AppBlue,
            "Notifikasi via Email", "Kirim pemberitahuan ke email kamu", notifEmail, onEmail)
        SettingsDivider()
        NotifToggleRow(Icons.AutoMirrored.Filled.Assignment, Color(0xFFEFF4FB), AppBlue,
            "Tugas Baru", "Ingatkan ketika ada tugas baru ditambahkan", notifTugas, onTugas)
        SettingsDivider()
        NotifToggleRow(Icons.Default.AccessTime, Color(0xFFFFF8EB), Color(0xFFD97706),
            "Pengingat Ujian", "Notifikasi H-1 sebelum ujian", notifUjian, onUjian)
        SettingsDivider()
        NotifToggleRow(Icons.Default.Campaign, Color(0xFFF0FAF4), Color(0xFF16A34A),
            "Pengumuman Kampus", "Berita dan pengumuman dari universitas", notifPengumuman, onPengumuman)
    }

    Spacer(modifier = Modifier.height(48.dp))
}

@Composable
private fun TabPrivasi() {
    SettingsPageHeader("Privasi", "Kontrol siapa yang bisa melihat informasi kamu.")
    Spacer(modifier = Modifier.height(28.dp))

    SettingsSectionCard("Visibilitas Profil", Icons.Default.PrivacyTip, Color(0xFFEFF4FB), AppBlue) {
        PrivasiOptionRow(Icons.Default.Person,   "Tampilkan Nama Lengkap",   "Nama kamu terlihat oleh pengguna lain", true)
        SettingsDivider()
        PrivasiOptionRow(Icons.Default.Email,    "Tampilkan Email",          "Email terlihat oleh dosen dan admin", false)
        SettingsDivider()
        PrivasiOptionRow(Icons.Default.School,   "Tampilkan Program Studi",  "Informasi prodi kamu terlihat oleh semua mahasiswa", true)
        SettingsDivider()
        PrivasiOptionRow(Icons.Default.BarChart, "Tampilkan Statistik Belajar", "Aktifitas belajar terlihat di leaderboard", true)
    }

    Spacer(modifier = Modifier.height(20.dp))

    SettingsSectionCard("Data & Riwayat", Icons.Default.Storage, Color(0xFFFFF8EB), Color(0xFFD97706)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text("Hapus Riwayat Aktivitas", fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = TextDark)
                Text("Hapus semua log aktivitas belajar kamu", fontSize = 12.sp, color = TextMuted)
            }
            Spacer(modifier = Modifier.width(12.dp))
            OutlinedButton(onClick = {}, shape = RoundedCornerShape(10.dp),
                border = BorderStroke(1.dp, BorderColor), contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp)) {
                Text("Hapus", color = Color(0xFF334155), fontSize = 13.sp, fontWeight = FontWeight.Medium)
            }
        }
    }

    Spacer(modifier = Modifier.height(48.dp))
}

@Composable
private fun TabBahasaWilayah(selectedLang: String, onLangChange: (String) -> Unit) {
    val languages = listOf("Indonesia", "English", "Melayu")
    val timezones = listOf("WIB (UTC+7)", "WITA (UTC+8)", "WIT (UTC+9)")
    var selectedTz by remember { mutableStateOf("WIB (UTC+7)") }

    SettingsPageHeader("Bahasa & Wilayah", "Sesuaikan bahasa tampilan dan zona waktu kamu.")
    Spacer(modifier = Modifier.height(28.dp))

    SettingsSectionCard("Bahasa", Icons.Default.Language, Color(0xFFEFF4FB), AppBlue) {
        Text("Pilih Bahasa", fontSize = 13.sp, color = TextMuted, fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.height(12.dp))
        languages.forEach { lang ->
            Surface(
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                shape = RoundedCornerShape(10.dp),
                color = if (selectedLang == lang) BgSelected else Color.Transparent,
                onClick = { onLangChange(lang) }
            ) {
                Row(modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(selected = selectedLang == lang, onClick = { onLangChange(lang) },
                        colors = RadioButtonDefaults.colors(selectedColor = AppBlue))
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(lang, fontSize = 14.sp, color = TextDark, fontWeight = if (selectedLang == lang) FontWeight.SemiBold else FontWeight.Normal)
                }
            }
        }
    }

    Spacer(modifier = Modifier.height(20.dp))

    SettingsSectionCard("Zona Waktu", Icons.Default.AccessTime, Color(0xFFFFF8EB), Color(0xFFD97706)) {
        Text("Pilih Zona Waktu", fontSize = 13.sp, color = TextMuted, fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.height(12.dp))
        timezones.forEach { tz ->
            Surface(
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                shape = RoundedCornerShape(10.dp),
                color = if (selectedTz == tz) BgSelected else Color.Transparent,
                onClick = { selectedTz = tz }
            ) {
                Row(modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(selected = selectedTz == tz, onClick = { selectedTz = tz },
                        colors = RadioButtonDefaults.colors(selectedColor = AppBlue))
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(tz, fontSize = 14.sp, color = TextDark, fontWeight = if (selectedTz == tz) FontWeight.SemiBold else FontWeight.Normal)
                }
            }
        }
    }

    Spacer(modifier = Modifier.height(48.dp))
}

@Composable
private fun TabTampilan(
    darkMode: Boolean, compactMode: Boolean,
    onDarkMode: (Boolean) -> Unit, onCompact: (Boolean) -> Unit
) {
    SettingsPageHeader("Tampilan", "Sesuaikan tampilan aplikasi sesuai selera kamu.")
    Spacer(modifier = Modifier.height(28.dp))

    SettingsSectionCard("Tema", Icons.Default.Palette, Color(0xFFF3F0FF), Color(0xFF7C3AED)) {
        NotifToggleRow(
            icon = Icons.Default.DarkMode, iconBg = Color(0xFFF3F0FF), iconTint = Color(0xFF7C3AED),
            title = "Mode Gelap", subtitle = "Gunakan tema gelap untuk antarmuka",
            checked = darkMode, onCheckedChange = onDarkMode
        )
        SettingsDivider()
        NotifToggleRow(
            icon = Icons.Default.ViewCompact, iconBg = Color(0xFFEFF4FB), iconTint = AppBlue,
            title = "Tampilan Kompak", subtitle = "Perkecil jarak antar elemen untuk lebih banyak konten",
            checked = compactMode, onCheckedChange = onCompact
        )
    }

    Spacer(modifier = Modifier.height(20.dp))

    SettingsSectionCard("Ukuran Teks", Icons.Default.TextFields, Color(0xFFF0FAF4), Color(0xFF16A34A)) {
        val sizes = listOf("Kecil", "Normal", "Besar")
        var selected by remember { mutableStateOf("Normal") }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            sizes.forEach { size ->
                val isSelected = selected == size
                Surface(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp),
                    color = if (isSelected) AppBlue else Color.Transparent,
                    border = BorderStroke(1.dp, if (isSelected) AppBlue else BorderColor),
                    onClick = { selected = size }
                ) {
                    Text(size, modifier = Modifier.padding(vertical = 12.dp),
                        fontSize = 13.sp, fontWeight = FontWeight.Medium,
                        color = if (isSelected) Color.White else TextMuted,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center)
                }
            }
        }
    }

    Spacer(modifier = Modifier.height(48.dp))
}

// ════════════════════════════════════════════════════════════════════════════
// SHARED COMPOSABLES
// ════════════════════════════════════════════════════════════════════════════

@Composable
private fun SettingsPageHeader(title: String, subtitle: String) {
    Text(title, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = TextDark)
    Text(subtitle, fontSize = 14.sp, color = TextMuted)
}

@Composable
fun SettingsSidebarItem(
    icon: ImageVector, label: String, selected: Boolean,
    isDestructive: Boolean = false, onClick: () -> Unit = {}
) {
    val textColor = when {
        isDestructive -> Color(0xFFE53935)
        selected      -> AppBlue
        else          -> Color(0xFF475569)
    }
    Surface(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 1.dp),
        color  = if (selected) BgSelected else Color.Transparent,
        shape  = RoundedCornerShape(10.dp),
        onClick = onClick
    ) {
        Row(modifier = Modifier.padding(horizontal = 12.dp, vertical = 11.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.width(3.dp).height(20.dp)
                .background(if (selected) AppBlue else Color.Transparent, RoundedCornerShape(2.dp)))
            Spacer(modifier = Modifier.width(10.dp))
            Icon(icon, contentDescription = null, tint = textColor, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(10.dp))
            Text(label, fontSize = 13.sp, fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal, color = textColor)
        }
    }
}

@Composable
fun SettingsSectionCard(
    title: String, icon: ImageVector, iconBg: Color, iconTint: Color,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(0.dp), border = BorderStroke(1.dp, BorderColor),
        shape = RoundedCornerShape(16.dp), modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(36.dp).background(iconBg, RoundedCornerShape(10.dp)),
                    contentAlignment = Alignment.Center) {
                    Icon(icon, contentDescription = null, tint = iconTint, modifier = Modifier.size(18.dp))
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(title, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = TextDark)
            }
            Spacer(modifier = Modifier.height(20.dp))
            HorizontalDivider(color = Color(0xFFF1F5F9))
            Spacer(modifier = Modifier.height(16.dp))
            content()
        }
    }
}

@Composable
fun SettingsInfoRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Text(label, fontSize = 13.sp, color = TextMuted, fontWeight = FontWeight.Medium)
        Text(value.ifBlank { "—" }, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = TextDark)
    }
}

@Composable
fun SettingsDivider() {
    HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp), color = Color(0xFFF1F5F9))
}

@Composable
fun NotifToggleRow(
    icon: ImageVector, iconBg: Color, iconTint: Color,
    title: String, subtitle: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
            Box(modifier = Modifier.size(38.dp).background(iconBg, RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center) {
                Icon(icon, contentDescription = null, tint = iconTint, modifier = Modifier.size(18.dp))
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column {
                Text(title, fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = TextDark)
                Text(subtitle, fontSize = 12.sp, color = TextMuted)
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        Switch(checked = checked, onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = AppBlue,
                uncheckedBorderColor = BorderColor))
    }
}

@Composable
private fun PrivasiOptionRow(icon: ImageVector, title: String, subtitle: String, defaultChecked: Boolean) {
    var checked by remember { mutableStateOf(defaultChecked) }
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
            Box(modifier = Modifier.size(38.dp).background(Color(0xFFEFF4FB), RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center) {
                Icon(icon, contentDescription = null, tint = AppBlue, modifier = Modifier.size(18.dp))
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column {
                Text(title, fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = TextDark)
                Text(subtitle, fontSize = 12.sp, color = TextMuted)
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        Switch(checked = checked, onCheckedChange = { checked = it },
            colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = AppBlue,
                uncheckedBorderColor = BorderColor))
    }
}

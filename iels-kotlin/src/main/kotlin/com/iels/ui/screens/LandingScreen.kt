package com.iels.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iels.ui.navigation.NavController
import com.iels.ui.navigation.Screen

@Composable
fun LandingScreen() {
        val scrollState = rememberScrollState()

        Column(
                modifier =
                        Modifier.fillMaxSize()
                                .background(Color(0xFFF8FAFC))
                                .verticalScroll(scrollState)
        ) {
                // --- 1. HEADER ---
                Row(
                        modifier =
                                Modifier.fillMaxWidth()
                                        .padding(horizontal = 64.dp, vertical = 24.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                ) {
                        Image(
                                painter = painterResource("IELS Logo 3.png"),
                                contentDescription = "IELS Logo",
                                modifier = Modifier.height(40.dp)
                        )

                        Row(horizontalArrangement = Arrangement.spacedBy(32.dp)) {
                                Text(
                                        "Fitur",
                                        color = Color(0xFF64748B),
                                        fontWeight = FontWeight.Medium,
                                        modifier = Modifier.clickable {}
                                )
                                Text(
                                        "Cara Kerja",
                                        color = Color(0xFF64748B),
                                        fontWeight = FontWeight.Medium,
                                        modifier = Modifier.clickable {}
                                )
                                Text(
                                        "Tentang",
                                        color = Color(0xFF64748B),
                                        fontWeight = FontWeight.Medium,
                                        modifier = Modifier.clickable {}
                                )
                        }
                }

                // --- 2. HERO BANNER ---
                Box(
                        modifier =
                                Modifier.fillMaxWidth()
                                        .padding(horizontal = 64.dp)
                                        .padding(top = 16.dp, bottom = 48.dp),
                        contentAlignment = Alignment.Center
                ) {
                        Image(
                                painter = painterResource("BannerLanding.png"),
                                contentDescription = "Hero Banner",
                                contentScale = ContentScale.FillWidth,
                                modifier =
                                        Modifier.fillMaxWidth(0.85f).clip(RoundedCornerShape(32.dp))
                        )
                }

                // --- 3. CALL TO ACTION BUTTONS ---
                Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                ) {
                        Button(
                                onClick = { NavController.navigateTo(Screen.Login) },
                                shape = RoundedCornerShape(8.dp),
                                colors =
                                        ButtonDefaults.buttonColors(
                                                containerColor = Color(0xFF3B82F6),
                                                contentColor = Color.White
                                        ),
                                modifier = Modifier.height(48.dp).padding(horizontal = 8.dp)
                        ) {
                                Text(
                                        "Login Portal",
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 16.dp)
                                )
                        }

                        Button(
                                onClick = { NavController.navigateTo(Screen.Login) },
                                shape = RoundedCornerShape(8.dp),
                                colors =
                                        ButtonDefaults.buttonColors(
                                                containerColor = Color(0xFFE2E8F0),
                                                contentColor = Color(0xFF0F172A)
                                        ),
                                modifier = Modifier.height(48.dp).padding(horizontal = 8.dp)
                        ) {
                                Icon(
                                        Icons.Default.School,
                                        contentDescription = "Student",
                                        modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                        "Masuk CBT Siswa",
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(end = 16.dp)
                                )
                        }
                }

                Spacer(modifier = Modifier.height(80.dp))

                // --- 4. DIDUKUNG OLEH ---
                Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                ) {
                        Text(
                                "DIDUKUNG OLEH",
                                color = Color(0xFF94A3B8),
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                letterSpacing = 2.sp
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Row(
                                horizontalArrangement = Arrangement.spacedBy(48.dp),
                                verticalAlignment = Alignment.CenterVertically
                        ) {
                                Image(
                                        painter = painterResource("logo unsrat.png"),
                                        contentDescription = "UNSRAT",
                                        modifier = Modifier.height(48.dp)
                                )
                                Image(
                                        painter = painterResource("Fatek.png"),
                                        contentDescription = "Fatek",
                                        modifier = Modifier.height(48.dp)
                                )
                                Image(
                                        painter = painterResource("IELS Logo 3.png"),
                                        contentDescription = "IELS",
                                        modifier = Modifier.height(36.dp)
                                )
                        }
                }

                Spacer(modifier = Modifier.height(100.dp))

                // --- 5. FEATURES SECTION ---
                Column(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 64.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                ) {
                        Text(
                                "",
                                color = Color(0xFF3B82F6),
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                letterSpacing = 2.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                                "Semua yang Anda butuhkan, dalam satu platform",
                                color = Color(0xFF0F172A),
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 26.sp,
                                textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                                "IELS CBT menyediakan solusi lengkap untuk pengelolaan ujian dengan\nantarmuka yang intuitif dan fitur yang powerful.",
                                color = Color(0xFF64748B),
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center,
                                lineHeight = 24.sp
                        )

                        Spacer(modifier = Modifier.height(64.dp))

                        // Feature Grid (2 rows, 3 columns)
                        Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                        ) {
                                Column(modifier = Modifier.widthIn(max = 1000.dp)) {
                                        Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.spacedBy(24.dp)
                                        ) {
                                                FeatureCard(
                                                        modifier = Modifier.weight(1f),
                                                        icon = Icons.Default.Dashboard,
                                                        title = "Dashboard Interaktif",
                                                        desc =
                                                                "Pantau status ujian, siswa, dan aktivitas real-time dalam satu tampilan terpusat."
                                                )
                                                FeatureCard(
                                                        modifier = Modifier.weight(1f),
                                                        icon = Icons.Default.Security,
                                                        title = "Keamanan Tingkat Tinggi",
                                                        desc =
                                                                "Proteksi SEB (Safe Exam Browser) untuk mencegah kecurangan saat ujian berlangsung."
                                                )
                                                FeatureCard(
                                                        modifier = Modifier.weight(1f),
                                                        icon = Icons.Default.Group,
                                                        title = "Manajemen Siswa",
                                                        desc =
                                                                "Kelola database siswa terpadu dengan mudah, lengkap dengan histori nilai."
                                                )
                                        }
                                        Spacer(modifier = Modifier.height(24.dp))
                                        Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.spacedBy(24.dp)
                                        ) {
                                                FeatureCard(
                                                        modifier = Modifier.weight(1f),
                                                        icon = Icons.Default.CheckCircle,
                                                        title = "Otomatisasi Penilaian",
                                                        desc =
                                                                "Sistem *grading* otomatis yang cepat dan transparan untuk soal pilihan ganda."
                                                )
                                                FeatureCard(
                                                        modifier = Modifier.weight(1f),
                                                        icon = Icons.Default.Assessment,
                                                        title = "Laporan & Analytics",
                                                        desc =
                                                                "Generator laporan KPI dan analisis nilai dalam format Excel/PDF secara instan."
                                                )
                                                FeatureCard(
                                                        modifier = Modifier.weight(1f),
                                                        icon = Icons.Default.Settings,
                                                        title = "Pengaturan Sistem",
                                                        desc =
                                                                "Konfigurasi profil admin, preferensi sistem, dan manajemen akses yang fleksibel."
                                                )
                                        }
                                }
                        }
                }

                Spacer(modifier = Modifier.height(64.dp))

                // --- 6. FOOTER ---
                Box(
                        modifier =
                                Modifier.fillMaxWidth()
                                        .background(Color(0xFF0F172A)) // Dark Slate
                                        .padding(horizontal = 64.dp, vertical = 48.dp)
                ) {
                        Column {
                                Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.Top
                                ) {
                                        Column(modifier = Modifier.weight(1f)) {
                                                Image(
                                                        painter =
                                                                painterResource("IELS Logo 3.png"),
                                                        contentDescription = "IELS",
                                                        modifier = Modifier.height(24.dp)
                                                )
                                                Spacer(modifier = Modifier.height(12.dp))
                                                Text(
                                                        "Sistem Ujian CBT Terintegrasi\nFakultas Teknik Universitas Sam Ratulangi",
                                                        color = Color(0xFF94A3B8),
                                                        fontSize = 12.sp,
                                                        lineHeight = 18.sp
                                                )
                                        }

                                        Row(
                                                modifier = Modifier.weight(1.5f),
                                                horizontalArrangement = Arrangement.SpaceEvenly
                                        ) {
                                                FooterColumn(
                                                        "PLATFORM",
                                                        listOf(
                                                                "Fitur",
                                                                "Cara Kerja",
                                                                "Login Portal"
                                                        )
                                                )
                                                FooterColumn(
                                                        "AKSES",
                                                        listOf("Admin Portal", "Student Portal")
                                                )
                                                FooterColumn(
                                                        "KONTAK",
                                                        listOf(
                                                                "Fakultas Teknik UNSRAT",
                                                                "Jl. Kampus UNSRAT Bahu",
                                                                "Manado, Sulawesi Utara 95115"
                                                        )
                                                )
                                        }
                                }

                                Spacer(modifier = Modifier.height(48.dp))
                                Divider(color = Color(0xFF334155))
                                Spacer(modifier = Modifier.height(16.dp))
                                Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                        Text(
                                                "© 2026 Fatek UNSRAT. All rights reserved.",
                                                color = Color(0xFF64748B),
                                                fontSize = 11.sp
                                        )
                                        Text(
                                                "POWERED BY IELS SYSTEM",
                                                color = Color(0xFF64748B),
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Bold
                                        )
                                }
                        }
                }
        }
}

@Composable
fun FeatureCard(modifier: Modifier = Modifier, icon: ImageVector, title: String, desc: String) {
        Card(
                modifier = modifier,
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                shape = RoundedCornerShape(12.dp)
        ) {
                Column(modifier = Modifier.padding(20.dp)) {
                        Box(
                                modifier =
                                        Modifier.size(36.dp)
                                                .background(
                                                        Color(0xFFEFF6FF),
                                                        RoundedCornerShape(8.dp)
                                                ),
                                contentAlignment = Alignment.Center
                        ) {
                                Icon(
                                        icon,
                                        contentDescription = title,
                                        tint = Color(0xFF3B82F6),
                                        modifier = Modifier.size(18.dp)
                                )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                                title,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = Color(0xFF0F172A)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(desc, color = Color(0xFF64748B), fontSize = 13.sp, lineHeight = 18.sp)
                }
        }
}

@Composable
fun FooterColumn(title: String, items: List<String>) {
        Column {
                Text(
                        title,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp,
                        letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(16.dp))
                items.forEach { item ->
                        Text(
                                item,
                                color = Color(0xFF94A3B8),
                                fontSize = 12.sp,
                                modifier = Modifier.padding(bottom = 8.dp)
                        )
                }
        }
}

package com.edudesk.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edudesk.ui.components.TopNavigationBar

data class FaqItem(val question: String, val answer: String)

@Composable
fun HelpScreen() {
    val faqs = listOf(
        FaqItem(
            "Bagaimana cara mendaftar mata kuliah?",
            "Anda dapat mendaftar mata kuliah melalui menu 'Pendaftaran Kelas' di halaman utama. Pilih mata kuliah yang ingin diambil, lalu klik tombol 'Daftar Sekarang'. Pastikan Anda memenuhi prasyarat yang diperlukan."
        ),
        FaqItem(
            "Bagaimana cara mengumpulkan tugas?",
            "Buka halaman 'Tugas' dari menu navigasi atau dari kartu mata kuliah Anda. Pilih tugas yang ingin dikumpulkan, lalu klik tombol 'Upload'. Format file yang didukung meliputi PDF, DOCX, ZIP, dan lainnya."
        ),
        FaqItem(
            "Bagaimana cara melihat nilai saya?",
            "Nilai dapat dilihat di halaman 'Mata Kuliah Saya' pada bagian rapor, atau melalui menu profil. Nilai biasanya diperbarui oleh dosen dalam 1-7 hari kerja setelah batas waktu pengumpulan."
        ),
        FaqItem(
            "Apa yang harus dilakukan jika lupa kata sandi?",
            "Klik tombol 'Lupa Kata Sandi' pada halaman login. Masukkan email atau NIM yang terdaftar, dan kami akan mengirimkan tautan reset kata sandi ke email Anda dalam beberapa menit."
        ),
        FaqItem(
            "Bagaimana cara menghubungi dosen?",
            "Anda dapat menghubungi dosen melalui fitur Pesan di aplikasi ini. Pergi ke menu 'Pesan' dan klik tombol ubah/edit untuk membuat pesan baru kepada dosen yang bersangkutan."
        ),
        FaqItem(
            "Apakah jadwal ujian dapat dilihat di aplikasi?",
            "Ya, jadwal ujian tersedia di halaman utama pada bagian 'Ujian Mendatang'. Anda juga akan menerima notifikasi pengingat H-1 sebelum ujian jika fitur notifikasi diaktifkan di pengaturan akun."
        ),
        FaqItem(
            "Bagaimana cara mengubah informasi profil?",
            "Pergi ke menu dropdown di sudut kanan atas, lalu pilih 'Edit Profil' atau 'Pengaturan Akun'. Anda dapat mengubah nama, foto profil, dan informasi kontak di sana."
        ),
    )

    var expandedFaq by remember { mutableStateOf<Int?>(null) }

    Scaffold(topBar = { TopNavigationBar() }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFE2E8EF))
                .verticalScroll(rememberScrollState())
        ) {
            // Hero Section
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF496E96))
                    .padding(horizontal = 48.dp, vertical = 48.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.SupportAgent, contentDescription = null,
                        tint = Color.White.copy(alpha = 0.9f), modifier = Modifier.size(56.dp))
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Bantuan & Dukungan", fontSize = 32.sp, fontWeight = FontWeight.Bold,
                        color = Color.White, textAlign = TextAlign.Center)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Kami siap membantu Anda. Cari jawaban atau hubungi tim dukungan kami.",
                        fontSize = 16.sp, color = Color.White.copy(alpha = 0.85f),
                        textAlign = TextAlign.Center)

                    Spacer(modifier = Modifier.height(24.dp))
                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        modifier = Modifier.width(500.dp),
                        placeholder = { Text("Cari pertanyaan...", color = Color.White.copy(0.7f)) },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Color.White.copy(0.7f)) },
                        shape = RoundedCornerShape(28.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color.White.copy(0.4f),
                            focusedBorderColor = Color.White,
                            unfocusedContainerColor = Color.White.copy(0.15f),
                            focusedContainerColor = Color.White.copy(0.2f),
                            unfocusedTextColor = Color.White,
                            focusedTextColor = Color.White
                        )
                    )
                }
            }

            // Quick Actions
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 48.dp, vertical = 32.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                HelpQuickCard(
                    icon = Icons.Default.Email,
                    title = "Email Support",
                    subtitle = "support@iels.unsrat.ac.id",
                    modifier = Modifier.weight(1f)
                )
                HelpQuickCard(
                    icon = Icons.Default.Phone,
                    title = "Telepon",
                    subtitle = "(0431) 827-xxx",
                    modifier = Modifier.weight(1f)
                )
                HelpQuickCard(
                    icon = Icons.Default.Schedule,
                    title = "Jam Operasional",
                    subtitle = "Sen–Jum, 08.00–16.00",
                    modifier = Modifier.weight(1f)
                )
                HelpQuickCard(
                    icon = Icons.Default.Chat,
                    title = "Live Chat",
                    subtitle = "Respon dalam 30 menit",
                    modifier = Modifier.weight(1f)
                )
            }

            // FAQ Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 48.dp)
            ) {
                Text("Pertanyaan yang Sering Diajukan", fontSize = 22.sp,
                    fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
                Spacer(modifier = Modifier.height(4.dp))
                Text("Jawaban untuk pertanyaan paling umum dari mahasiswa.",
                    fontSize = 14.sp, color = Color(0xFF64748B))
                Spacer(modifier = Modifier.height(20.dp))

                faqs.forEachIndexed { index, faq ->
                    FaqCard(
                        faq = faq,
                        isExpanded = expandedFaq == index,
                        onToggle = { expandedFaq = if (expandedFaq == index) null else index }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }

            // Contact Card
            Spacer(modifier = Modifier.height(16.dp))
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFFEFF4FB)),
                elevation = CardDefaults.cardElevation(0.dp),
                border = BorderStroke(1.dp, Color(0xFFC7D9EF)),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 48.dp)
            ) {
                Row(
                    modifier = Modifier.padding(28.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.HelpOutline, contentDescription = null,
                        tint = Color(0xFF496E96), modifier = Modifier.size(40.dp))
                    Spacer(modifier = Modifier.width(20.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Tidak menemukan jawaban yang dicari?",
                            fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF1E293B))
                        Text("Tim kami siap membantu Anda secara langsung.",
                            fontSize = 13.sp, color = Color(0xFF64748B))
                    }
                    Button(
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF496E96)),
                        shape = RoundedCornerShape(10.dp),
                        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp)
                    ) {
                        Text("Hubungi Kami", color = Color.White, fontWeight = FontWeight.SemiBold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}

@Composable
fun HelpQuickCard(icon: ImageVector, title: String, subtitle: String, modifier: Modifier = Modifier) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(0.dp),
        border = BorderStroke(1.dp, Color(0xFFDDE3EA)),
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(Color(0xFFEFF4FB), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = Color(0xFF496E96), modifier = Modifier.size(24.dp))
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(title, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF1E293B))
            Spacer(modifier = Modifier.height(4.dp))
            Text(subtitle, fontSize = 12.sp, color = Color(0xFF64748B))
        }
    }
}

@Composable
fun FaqCard(faq: FaqItem, isExpanded: Boolean, onToggle: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(0.dp),
        border = BorderStroke(1.dp, if (isExpanded) Color(0xFF496E96).copy(0.4f) else Color(0xFFDDE3EA)),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onToggle)
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(faq.question, fontWeight = FontWeight.SemiBold, fontSize = 15.sp,
                    color = Color(0xFF1E293B), modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.width(12.dp))
                Icon(
                    if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = null, tint = Color(0xFF496E96)
                )
            }
            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Column {
                    HorizontalDivider(color = Color(0xFFF1F5F9))
                    Text(
                        faq.answer,
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp),
                        fontSize = 14.sp, color = Color(0xFF475569), lineHeight = 24.sp
                    )
                }
            }
        }
    }
}

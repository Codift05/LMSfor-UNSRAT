package com.edudesk.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edudesk.ui.components.TopNavigationBar
import com.edudesk.ui.navigation.NavController
import com.edudesk.ui.navigation.Screen

data class Message(
    val id: Int,
    val sender: String,
    val senderInitials: String,
    val subject: String,
    val preview: String,
    val time: String,
    val isRead: Boolean = false,
    val isFromSystem: Boolean = false
)

@Composable
fun MessagesScreen() {
    val messages = listOf(
        Message(1, "Prof. Dr. Ir. Budi, M.Sc.", "PB", "Pengumuman UTS Struktur Data",
            "Ujian tengah semester akan dilaksanakan pada tanggal 25 Maret 2026 pukul 08.00 WITA...",
            "13:45", isRead = false),
        Message(2, "Sistem IELS", "SL", "Tugas Baru: Laporan Praktikum",
            "Anda memiliki tugas baru yang harus dikumpulkan sebelum 20 Maret 2026...",
            "10:20", isRead = false, isFromSystem = true),
        Message(3, "Dr. Eng. Rizal, S.T., M.T.", "DR", "Feedback Tugas PBO",
            "Tugas Anda sudah dinilai. Silakan cek portal akademik untuk melihat nilai dan komentar...",
            "Kemarin", isRead = true),
        Message(4, "Ir. Maria, S.Kom., M.Kom.", "IM", "Materi Tambahan Web Development",
            "Saya telah mengunggah materi tambahan mengenai REST API dan framework modern...",
            "Kemarin", isRead = true),
        Message(5, "Sistem IELS", "SL", "Pengingat: Pembayaran SKS",
            "Batas pembayaran SKS semester ini adalah 30 Maret 2026. Harap segera lakukan...",
            "15 Mar", isRead = true, isFromSystem = true),
        Message(6, "Alexander S. M. Lumenta, S.T., M.T.", "AL", "Jadwal Praktikum Jaringan",
            "Jadwal praktikum jaringan komputer telah diperbarui. Silakan cek jadwal terbaru...",
            "14 Mar", isRead = true),
    )

    var selectedMessage by remember { mutableStateOf<Message?>(messages[0]) }

    Scaffold(topBar = { TopNavigationBar() }) { padding ->
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFE2E8EF))
        ) {
            // LEFT PANEL: Message List
            Column(
                modifier = Modifier
                    .width(360.dp)
                    .fillMaxHeight()
                    .background(Color.White)
            ) {
                // Header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Pesan", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
                        Text("${messages.count { !it.isRead }} belum dibaca",
                            fontSize = 12.sp, color = Color(0xFF64748B))
                    }
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Edit, contentDescription = null, tint = Color(0xFF496E96))
                    }
                }

                HorizontalDivider(color = Color(0xFFE2E8F0))

                // Filter tabs
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilterChip(selected = true, onClick = {},
                        label = { Text("Semua", fontSize = 12.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Color(0xFF496E96),
                            selectedLabelColor = Color.White
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            borderColor = Color(0xFFE2E8F0),
                            selectedBorderColor = Color.Transparent,
                            enabled = true, selected = true
                        )
                    )
                    FilterChip(selected = false, onClick = {},
                        label = { Text("Belum Dibaca", fontSize = 12.sp) },
                        colors = FilterChipDefaults.filterChipColors(),
                        border = FilterChipDefaults.filterChipBorder(
                            borderColor = Color(0xFFE2E8F0),
                            selectedBorderColor = Color.Transparent,
                            enabled = true, selected = false
                        )
                    )
                }

                HorizontalDivider(color = Color(0xFFE2E8F0))

                // Message list
                LazyColumn {
                    items(messages) { msg ->
                        MessageListItem(
                            message = msg,
                            isSelected = selectedMessage?.id == msg.id,
                            onClick = { selectedMessage = msg }
                        )
                        HorizontalDivider(color = Color(0xFFF1F5F9))
                    }
                }
            }

            // Thin divider
            Spacer(modifier = Modifier.width(1.dp).fillMaxHeight().background(Color(0xFFDDE3EA)))

            // RIGHT PANEL: Message Detail
            if (selectedMessage != null) {
                val msg = selectedMessage!!
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(Color.White)
                        .padding(32.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(modifier = Modifier.size(48.dp), shape = CircleShape,
                            color = if (msg.isFromSystem) Color(0xFF496E96) else Color(0xFF1E293B)) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(msg.senderInitials, color = Color.White,
                                    fontSize = 16.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(msg.sender, fontWeight = FontWeight.Bold,
                                fontSize = 16.sp, color = Color(0xFF1E293B))
                            Text(msg.time, fontSize = 12.sp, color = Color(0xFF64748B))
                        }
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            IconButton(onClick = {}) {
                                Icon(Icons.Default.Reply, contentDescription = null, tint = Color(0xFF64748B))
                            }
                            IconButton(onClick = {}) {
                                Icon(Icons.Default.Delete, contentDescription = null, tint = Color(0xFF64748B))
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                    HorizontalDivider(color = Color(0xFFE2E8F0))
                    Spacer(modifier = Modifier.height(24.dp))

                    Text(msg.subject, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        msg.preview + "\n\nLorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.\n\nDuis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\n\nSalam hormat,\n${msg.sender}",
                        fontSize = 15.sp, color = Color(0xFF475569), lineHeight = 26.sp
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // Reply box
                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        modifier = Modifier.fillMaxWidth().height(120.dp),
                        placeholder = { Text("Tulis balasan...", color = Color(0xFF94A3B8)) },
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF496E96),
                            unfocusedBorderColor = Color(0xFFE2E8F0)
                        )
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                        Button(
                            onClick = {},
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF496E96)),
                            shape = RoundedCornerShape(10.dp),
                            contentPadding = PaddingValues(horizontal = 28.dp, vertical = 12.dp)
                        ) {
                            Icon(Icons.Default.Send, contentDescription = null,
                                tint = Color.White, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Kirim", color = Color.White, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MessageListItem(message: Message, isSelected: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .background(if (isSelected) Color(0xFFF0F4F8) else Color.Transparent)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.Top
    ) {
        // Unread indicator
        Box(
            modifier = Modifier
                .padding(top = 8.dp)
                .size(8.dp)
                .clip(CircleShape)
                .background(if (!message.isRead) Color(0xFF496E96) else Color.Transparent)
        )
        Spacer(modifier = Modifier.width(10.dp))

        // Avatar
        Surface(modifier = Modifier.size(40.dp), shape = CircleShape,
            color = if (message.isFromSystem) Color(0xFF496E96).copy(alpha = 0.15f) else Color(0xFFE2E8F0)) {
            Box(contentAlignment = Alignment.Center) {
                Text(message.senderInitials,
                    color = if (message.isFromSystem) Color(0xFF496E96) else Color(0xFF64748B),
                    fontSize = 13.sp, fontWeight = FontWeight.Bold)
            }
        }
        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(message.sender, fontWeight = if (!message.isRead) FontWeight.Bold else FontWeight.Medium,
                    fontSize = 13.sp, color = Color(0xFF1E293B),
                    maxLines = 1, overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f))
                Text(message.time, fontSize = 11.sp,
                    color = if (!message.isRead) Color(0xFF496E96) else Color(0xFF94A3B8),
                    fontWeight = if (!message.isRead) FontWeight.SemiBold else FontWeight.Normal)
            }
            Spacer(modifier = Modifier.height(2.dp))
            Text(message.subject, fontWeight = if (!message.isRead) FontWeight.SemiBold else FontWeight.Normal,
                fontSize = 13.sp, color = Color(0xFF334155), maxLines = 1, overflow = TextOverflow.Ellipsis)
            Spacer(modifier = Modifier.height(2.dp))
            Text(message.preview, fontSize = 12.sp, color = Color(0xFF94A3B8),
                maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
    }
}

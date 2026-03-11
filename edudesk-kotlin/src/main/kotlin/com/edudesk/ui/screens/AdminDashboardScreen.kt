package com.edudesk.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edudesk.ui.components.DashboardBanner
import com.edudesk.ui.components.TopNavigationBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboardScreen() {
    Scaffold(
        topBar = { TopNavigationBar() }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFF8FAFC)) // Slate 50
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 48.dp, vertical = 24.dp)
        ) {
            DashboardBanner("Selamat Datang Kembali,\nAdmin Prodi")
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Statistics Cards
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                StatCard("Total Pengguna", "1,245", Modifier.weight(1f))
                StatCard("Total Kursus Aktif", "86", Modifier.weight(1f))
                StatCard("Total Sesi Hari Ini", "432", Modifier.weight(1f))
            }
            
            Spacer(modifier = Modifier.height(48.dp))
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(32.dp)) {
                // Left Column: Manajemen Pengguna
                Column(modifier = Modifier.weight(1.5f)) {
                    Text("Manajemen Pengguna", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(2.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(24.dp)) {
                            // Header Tab style
                            Row(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)) {
                                Text("Mahasiswa Aktif", fontWeight = FontWeight.Bold, color = Color(0xFF6366F1), modifier = Modifier.padding(end = 24.dp))
                                Text("Dosen", fontWeight = FontWeight.Medium, color = Color.Gray)
                                Spacer(modifier = Modifier.weight(1f))
                                Text("Kelola", fontWeight = FontWeight.Medium, color = Color(0xFF8B5CF6))
                            }
                            Divider(color = Color(0xFFE2E8F0))
                            
                            // Rows
                            UserRow("Budi Santoso", "Mahasiswa", "Aktif", true)
                            UserRow("Rina Melati", "Mahasiswa", "Aktif", true)
                            UserRow("Andi Darmawan", "Mahasiswa", "Nonaktif", false)
                            UserRow("Dr. Eng. Rizal, S.T., M.T.", "Dosen", "Aktif", true)
                        }
                    }
                }
                
                // Right Column: Persetujuan Kelas
                Column(modifier = Modifier.weight(1f)) {
                    Text("Persetujuan Kelas", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(2.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            PendingClassRow("Sistem Operasi", "Dr. Eng. Rizal, S.T., M.T.")
                            PendingClassRow("Basis Data Lanjut", "Prof. Dr. Ir. Budi, M.Sc.")
                            PendingClassRow("Manajemen Proyek TI", "Ir. Maria, S.Kom., M.Kom.")
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(48.dp))
            
            // Laporan Sistem
            Text("Laporan Sistem (7 Hari Terakhir)", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Pertumbuhan Pendaftaran", fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Box(
                        modifier = Modifier.fillMaxWidth().height(250.dp).background(Color(0xFFF1F5F9), RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Wide Line Chart Placeholder (Pendaftaran vs Kelulusan)", color = Color.Gray)
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(64.dp))
        }
    }
}

@Composable
fun UserRow(name: String, role: String, status: String, isActive: Boolean) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier.size(36.dp),
            shape = CircleShape,
            color = Color(0xFFE2E8F0)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(name.take(1), fontWeight = FontWeight.Bold, color = Color(0xFF475569))
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(name, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Text(role, color = Color.Gray, fontSize = 12.sp)
        }
        
        Surface(
            color = if (isActive) Color(0xFFDCFCE7) else Color(0xFFFEE2E2), // Green vs Red Tint
            shape = RoundedCornerShape(4.dp)
        ) {
            Text(
                status,
                color = if (isActive) Color(0xFF16A34A) else Color(0xFFDC2626),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }
    }
    Divider(color = Color(0xFFF1F5F9))
}

@Composable
fun PendingClassRow(title: String, instructor: String) {
    Column(modifier = Modifier.padding(bottom = 16.dp)) {
        Text(title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        Text("Oleh: $instructor", color = Color.Gray, fontSize = 12.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF16A34A)),
                shape = RoundedCornerShape(4.dp),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp),
                modifier = Modifier.height(32.dp)
            ) {
                Text("Setujui", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
            OutlinedButton(
                onClick = {},
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFDC2626)),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFDC2626)),
                shape = RoundedCornerShape(4.dp),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp),
                modifier = Modifier.height(32.dp)
            ) {
                Text("Tolak", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Divider(color = Color(0xFFF1F5F9))
    }
}

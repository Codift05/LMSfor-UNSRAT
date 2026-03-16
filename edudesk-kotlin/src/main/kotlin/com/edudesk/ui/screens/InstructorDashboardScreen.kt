package com.edudesk.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edudesk.ui.components.DashboardBanner
import com.edudesk.ui.components.TopNavigationBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InstructorDashboardScreen() {
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
            DashboardBanner("Selamat Datang Kembali, Instruktur")
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Statistics Cards
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                StatCard("Total Kelas", "37", Modifier.weight(1f))
                StatCard("Total Mahasiswa", "236", Modifier.weight(1f))
                StatCard("Total Materi Diunggah", "128", Modifier.weight(1f))
            }
            
            Spacer(modifier = Modifier.height(48.dp))
            
            // Kelas Saya section
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Kelas Saya", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF8B5CF6),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Kelola Kelas", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Grid of Classes
            // We use a Column of Rows to simulate grid in a verticalScroll, 
            // LazyVerticalGrid inside verticalScroll requires fixed height
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.fillMaxWidth()) {
                    InstructorClassCard("Pemrograman Berorientasi Objek", 392, Modifier.weight(1f))
                    InstructorClassCard("Struktur Data", 252, Modifier.weight(1f))
                    InstructorClassCard("Kecerdasan Buatan", 364, Modifier.weight(1f))
                    Spacer(modifier = Modifier.weight(1f)) // Empty spot to align left
                }
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.fillMaxWidth()) {
                    InstructorClassCard("Pengembangan Aplikasi Web", 198, Modifier.weight(1f))
                    InstructorClassCard("Jaringan Komputer Lanjut", 196, Modifier.weight(1f))
                    Spacer(modifier = Modifier.weight(1f))
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
            
            Spacer(modifier = Modifier.height(48.dp))
            
            // Manajemen Materi
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Manajemen Materi", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF8B5CF6),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Unggah Materi", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(32.dp).fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    UploadZone("Drag ad drop\nfile video")
                    UploadZone("Drag and drops\nfiles")
                    UploadZone("Total Materi\nDiunggah")
                }
            }
            
            Spacer(modifier = Modifier.height(48.dp))
            
            // Pembuat Evaluasi
            Text("Pembuat Evaluasi", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(modifier = Modifier.height(250.dp)) {
                    // Left Tabs
                    Column(
                        modifier = Modifier
                            .width(200.dp)
                            .fillMaxHeight()
                            .background(Color(0xFFF1F5F9))
                    ) {
                        TabItem("Kelas", true)
                        TabItem("Materi", false)
                        TabItem("Pemahaman Bab", false)
                        TabItem("Evaluasi", false)
                    }
                    
                    // Right Form
                    Column(modifier = Modifier.weight(1f).padding(24.dp)) {
                        Text("Nama Evaluasi", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedTextField(
                            value = "", onValueChange = {},
                            placeholder = { Text("Kuis Pemrograman...", color = Color.Gray, fontSize = 14.sp) },
                            modifier = Modifier.fillMaxWidth().height(48.dp),
                            shape = RoundedCornerShape(8.dp)
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Text("Topik Evaluasi", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedTextField(
                            value = "", onValueChange = {},
                            placeholder = { Text("Misal: Inheritance & Polymorphism...", color = Color.Gray, fontSize = 14.sp) },
                            modifier = Modifier.fillMaxWidth().height(48.dp),
                            shape = RoundedCornerShape(8.dp)
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Text("Deskripsi", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedTextField(
                            value = "", onValueChange = {},
                            placeholder = { Text("Tuliskan deskripsi lengkap...", color = Color.Gray, fontSize = 14.sp) },
                            modifier = Modifier.fillMaxWidth().height(80.dp),
                            shape = RoundedCornerShape(8.dp),
                            maxLines = 3
                        )
                        
                        Spacer(modifier = Modifier.height(24.dp))
                        
                        Button(
                            onClick = {},
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF8B5CF6),
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("Buat Evaluasi", color = Color.White)
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(48.dp))
            
            // Papan Penilaian
            Text("Papan Penilaian", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    // Header
                    Row(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)) {
                        Text("Kelas", fontWeight = FontWeight.Bold, modifier = Modifier.weight(2f))
                        Text("Status", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
                        Text("Grade", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
                        Text("Feedback", fontWeight = FontWeight.Bold, modifier = Modifier.weight(3f))
                    }
                    Divider(color = Color(0xFFE2E8F0))
                    
                    // Rows
                    GradingRow("Jovanka Angelina", "Av1", "10", true)
                    GradingRow("Rizky Mahendra", "In4", "8", true)
                    GradingRow("Sarah Toding", "Av1", "", false)
                    GradingRow("Miftahuddin", "In4", "", false)
                    GradingRow("Adolf Paath", "In4", "8", true, true)
                }
            }
            
            Spacer(modifier = Modifier.height(48.dp))
            
            // Laporan Performa Kelas
            Text("Laporan Performa Kelas", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(horizontalArrangement = Arrangement.spacedBy(24.dp), modifier = Modifier.fillMaxWidth()) {
                ChartCard(Modifier.weight(1f))
                ChartCard(Modifier.weight(1f))
            }
            
            Spacer(modifier = Modifier.height(64.dp))
        }
    }
}

// Subcomponents

@Composable
fun StatCard(label: String, value: String, modifier: Modifier = Modifier) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        modifier = modifier.height(100.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp).fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(label, color = Color.Gray, fontSize = 14.sp, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(value, fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
        }
    }
}

@Composable
fun InstructorClassCard(title: String, reviews: Int, modifier: Modifier = Modifier) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        modifier = modifier
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(Color(0xFF2B3A4A)) // Slate 800
            ) {
                IconButton(onClick = {}, modifier = Modifier.align(Alignment.TopEnd)) {
                    Icon(Icons.Default.MoreVert, contentDescription = null, tint = Color.White)
                }
            }
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    repeat(5) {
                        Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFBBF24), modifier = Modifier.size(14.dp))
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("($reviews)", fontSize = 12.sp, color = Color.Gray)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    SmallOutlineButton("Edit Kelas")
                    SmallOutlineButton("Materi")
                    SmallOutlineButton("Siswa")
                }
            }
        }
    }
}

@Composable
fun SmallOutlineButton(text: String) {
    Surface(
        shape = RoundedCornerShape(4.dp),
        border = border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(4.dp)),
        color = Color.White
    ) {
        Text(
            text,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            color = Color(0xFF475569)
        )
    }
}

private fun border(width: androidx.compose.ui.unit.Dp, color: Color, shape: androidx.compose.ui.graphics.Shape): androidx.compose.foundation.BorderStroke {
    return androidx.compose.foundation.BorderStroke(width, color)
}

@Composable
fun UploadZone(text: String) {
    Box(
        modifier = Modifier
            .size(160.dp, 120.dp)
            .border(2.dp, Color(0xFFE2E8F0), RoundedCornerShape(12.dp))
            .background(Color(0xFFF8FAFC), RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text,
            textAlign = TextAlign.Center,
            color = Color.Gray,
            fontSize = 14.sp
        )
    }
}

@Composable
fun TabItem(text: String, isSelected: Boolean) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(if (isSelected) Color(0xFFE0E7FF) else Color.Transparent)
            .border(
                border = androidx.compose.foundation.BorderStroke(if (isSelected) 2.dp else 0.dp, if (isSelected) Color(0xFF6366F1) else Color.Transparent)
            )
            .padding(vertical = 16.dp, horizontal = 24.dp)
    ) {
        Text(
            text,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = if (isSelected) Color(0xFF6366F1) else Color(0xFF475569) // Indigo vs Slate
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GradingRow(name: String, status: String, grade: String, hasInput: Boolean, hasSubmit: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(modifier = Modifier.weight(2f), verticalAlignment = Alignment.CenterVertically) {
             Surface(
                modifier = Modifier.size(32.dp),
                shape = CircleShape,
                color = Color(0xFF1E293B)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text("MS", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(name, fontWeight = FontWeight.Medium)
        }
        
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
            Surface(
                color = if (status == "Av1") Color(0xFFDCFCE7) else Color(0xFFF1F5F9), // Greenish vs Slate
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    status,
                    color = if (status == "Av1") Color(0xFF16A34A) else Color(0xFF475569),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                )
            }
        }
        
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
            OutlinedTextField(
                value = grade,
                onValueChange = {},
                modifier = Modifier.width(60.dp).height(40.dp),
                shape = RoundedCornerShape(8.dp)
            )
        }
        
        Row(modifier = Modifier.weight(3f), verticalAlignment = Alignment.CenterVertically) {
            if (hasInput && !hasSubmit) {
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    placeholder = { Text("Input s feedback", fontSize = 12.sp) },
                    modifier = Modifier.fillMaxWidth().height(40.dp),
                    shape = RoundedCornerShape(8.dp)
                )
            } else if (!hasInput) {
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    placeholder = { Text("Prediant feedback", fontSize = 12.sp) },
                    modifier = Modifier.fillMaxWidth().height(80.dp),
                    shape = RoundedCornerShape(8.dp),
                    maxLines = 3
                )
            } else if (hasSubmit) {
                 OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    modifier = Modifier.weight(1f).height(40.dp),
                    shape = RoundedCornerShape(8.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF8B5CF6),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.height(40.dp)
                ) {
                    Text("Submit", fontSize = 12.sp, color = Color.White)
                }
            }
        }
    }
}

@Composable
fun ChartCard(modifier: Modifier = Modifier) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Class Performa", fontWeight = FontWeight.Bold)
                Surface(
                    color = Color(0xFFF8FAFC),
                    shape = RoundedCornerShape(4.dp),
                    border = border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(4.dp))
                ) {
                    Text("Class v", modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp), fontSize = 12.sp)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier.fillMaxWidth().height(150.dp).background(Color(0xFFF1F5F9), RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text("Dual Chart Placeholder", color = Color.Gray)
            }
        }
    }
}

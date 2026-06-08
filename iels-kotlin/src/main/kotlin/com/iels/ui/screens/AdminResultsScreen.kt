package com.iels.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iels.models.ExamResultDetailDto
import com.iels.services.ExamService
import com.iels.services.PdfExportService
import com.iels.ui.components.AdminLayout
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.swing.JFileChooser

@Composable
fun AdminResultsScreen() {
    val coroutineScope = rememberCoroutineScope()
    val examService = remember { ExamService() }
    val pdfService = remember { PdfExportService() }
    
    var results by remember { mutableStateOf<List<ExamResultDetailDto>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var showSuccessMessage by remember { mutableStateOf(false) }

    fun refreshData() {
        isLoading = true
        coroutineScope.launch {
            results = examService.getResults()
            isLoading = false
        }
    }

    LaunchedEffect(Unit) {
        refreshData()
    }
    
    fun exportToPdf() {
        coroutineScope.launch(Dispatchers.IO) {
            val fileChooser = JFileChooser()
            fileChooser.dialogTitle = "Simpan Laporan PDF"
            fileChooser.selectedFile = java.io.File("Rekap_Hasil_Ujian.pdf")
            
            val userSelection = fileChooser.showSaveDialog(null)
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                var fileToSave = fileChooser.selectedFile
                if (!fileToSave.name.lowercase().endsWith(".pdf")) {
                    fileToSave = java.io.File(fileToSave.absolutePath + ".pdf")
                }
                
                val success = pdfService.exportResultsToPdf(results, fileToSave.absolutePath)
                if (success) {
                    showSuccessMessage = true
                    kotlinx.coroutines.delay(3000)
                    showSuccessMessage = false
                }
            }
        }
    }

    AdminLayout(activeMenu = "Hasil Ujian") {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 48.dp, vertical = 32.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Rekap & Peringkat Ujian", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Lihat hasil akhir dan peringkat mahasiswa berdasarkan nilai skor ujian.", color = Color(0xFF64748B), fontSize = 15.sp)
                    }
                    
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        Button(
                            onClick = { refreshData() },
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE2E8F0), contentColor = Color(0xFF0F172A))
                        ) {
                            Text("Refresh", fontWeight = FontWeight.SemiBold)
                        }
                        
                        Button(
                            onClick = { exportToPdf() },
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981), contentColor = Color.White),
                            enabled = results.isNotEmpty()
                        ) {
                            Icon(Icons.Default.Download, contentDescription = "Export PDF", modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Export to PDF", fontWeight = FontWeight.SemiBold)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(48.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                    elevation = CardDefaults.cardElevation(0.dp)
                ) {
                    Column(modifier = Modifier.fillMaxWidth().padding(32.dp)) {
                        if (isLoading) {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                        } else if (results.isEmpty()) {
                            Text("Belum ada data hasil ujian.", fontSize = 14.sp, color = Color(0xFF94A3B8))
                        } else {
                            // Header
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("PERINGKAT", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF94A3B8), modifier = Modifier.weight(1f))
                                Text("NAMA MAHASISWA", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF94A3B8), modifier = Modifier.weight(2.5f))
                                Text("UJIAN", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF94A3B8), modifier = Modifier.weight(2f))
                                Text("SKOR", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF94A3B8), modifier = Modifier.weight(1f))
                            }
                            
                            Divider(color = Color(0xFFF1F5F9))
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            results.forEachIndexed { index, result ->
                                Row(
                                    modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    val rankColor = when (index) {
                                        0 -> Color(0xFFF59E0B) // Gold
                                        1 -> Color(0xFF94A3B8) // Silver
                                        2 -> Color(0xFFB45309) // Bronze
                                        else -> Color(0xFF0F172A)
                                    }
                                    
                                    Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                                        Surface(
                                            shape = RoundedCornerShape(8.dp),
                                            color = if (index < 3) rankColor.copy(alpha = 0.1f) else Color(0xFFF1F5F9),
                                            modifier = Modifier.size(32.dp)
                                        ) {
                                            Box(contentAlignment = Alignment.Center) {
                                                Text("#${index + 1}", color = rankColor, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                            }
                                        }
                                    }
                                    
                                    Column(modifier = Modifier.weight(2.5f)) {
                                        Text(result.userName, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF0F172A))
                                        Text(result.userNim, fontSize = 12.sp, color = Color(0xFF64748B))
                                    }
                                    
                                    Text(result.examTitle, fontSize = 14.sp, color = Color(0xFF475569), modifier = Modifier.weight(2f))
                                    
                                    val scoreColor = if (result.score >= 70) Color(0xFF10B981) else Color(0xFFEF4444)
                                    Text("${result.score}", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = scoreColor, modifier = Modifier.weight(1f))
                                }
                                Divider(color = Color(0xFFF1F5F9))
                            }
                        }
                    }
                }
            }
            
            if (showSuccessMessage) {
                Snackbar(
                    modifier = Modifier.align(Alignment.BottomCenter).padding(24.dp),
                    containerColor = Color(0xFF10B981),
                    contentColor = Color.White
                ) {
                    Text("Laporan PDF berhasil disimpan!", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

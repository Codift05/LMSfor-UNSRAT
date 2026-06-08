package com.iels.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iels.models.Exam
import com.iels.models.Question
import com.iels.services.ExamService
import com.iels.services.SessionManager
import com.iels.ui.components.AdminLayout
import com.iels.utils.PdfParser
import kotlinx.coroutines.launch
import java.awt.FileDialog
import java.awt.Frame
import java.io.File

@Composable
private fun BuilderTextField(
    value: String, 
    onValueChange: (String) -> Unit, 
    placeholder: String,
    singleLine: Boolean = true,
    modifier: Modifier = Modifier.fillMaxWidth()
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder, color = Color(0xFF94A3B8), fontSize = 13.sp) },
        modifier = modifier,
        singleLine = singleLine,
        shape = RoundedCornerShape(8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFF496E96),
            unfocusedBorderColor = Color(0xFFE2E8F0),
            focusedLabelColor = Color(0xFF496E96),
            unfocusedLabelColor = Color(0xFF64748B),
            cursorColor = Color(0xFF496E96),
            unfocusedContainerColor = Color(0xFFF8FAFC),
            focusedContainerColor = Color.White
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboardScreen() {
    val coroutineScope = rememberCoroutineScope()
    val examService = remember { ExamService() }
    
    // Exam State
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var duration by remember { mutableStateOf("60") }
    var token by remember { mutableStateOf("") }
    var questions by remember { mutableStateOf<List<Question>>(emptyList()) }
    var isPublishing by remember { mutableStateOf(false) }

    fun importPdf() {
        try {
            val dialog = FileDialog(null as Frame?, "Pilih File PDF Bank Soal", FileDialog.LOAD)
            dialog.file = "*.pdf"
            dialog.isVisible = true
            if (dialog.file != null) {
                val file = File(dialog.directory, dialog.file)
                val importedQuestions = PdfParser.extractQuestionsFromPdf(file)
                if (importedQuestions.isNotEmpty()) {
                    val newList = questions.toMutableList()
                    newList.addAll(importedQuestions)
                    questions = newList
                }
            }
        } catch (e: Exception) {
            println("Dialog error: ${e.message}")
        }
    }

    AdminLayout(activeMenu = "Pembuat Ujian") {
        // HEADER
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 48.dp, vertical = 32.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text("Buat Ujian Baru", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
                Spacer(modifier = Modifier.height(8.dp))
                Text("Konfigurasikan detail ujian dan susun bank soal Anda.", fontSize = 14.sp, color = Color(0xFF64748B))
            }

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp), verticalAlignment = Alignment.CenterVertically) {
                OutlinedButton(
                    onClick = { importPdf() },
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                    colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White, contentColor = Color(0xFF496E96))
                ) {
                    Icon(Icons.Default.UploadFile, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Impor PDF", fontWeight = FontWeight.SemiBold)
                }

                OutlinedButton(
                    onClick = { /* Save Draft */ },
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                    colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White, contentColor = Color(0xFF0F172A))
                ) {
                    Text("Simpan Konsep", fontWeight = FontWeight.SemiBold)
                }

                Button(
                    onClick = {
                        if (title.isBlank() || token.isBlank() || questions.isEmpty()) return@Button
                        isPublishing = true
                        coroutineScope.launch {
                            val newExam = Exam(
                                title = title,
                                durationMinutes = duration.toIntOrNull() ?: 60,
                                token = token,
                                instructorId = SessionManager.currentUser?.id ?: 1,
                                questions = questions
                            )
                            if (examService.createExam(newExam)) {
                                title = ""
                                duration = "60"
                                token = ""
                                questions = emptyList()
                            }
                            isPublishing = false
                        }
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF496E96), contentColor = Color.White, disabledContainerColor = Color(0xFFCBD5E1)),
                    enabled = !isPublishing
                ) {
                    Icon(Icons.Default.Publish, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(if (isPublishing) "Menerbitkan..." else "Terbitkan Ujian", fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        }

        HorizontalDivider(color = Color(0xFFF1F5F9))

        // CONTENT AREA
        Row(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(48.dp),
            horizontalArrangement = Arrangement.spacedBy(40.dp)
        ) {
            // LEFT CONFIG COLUMN
            Column(modifier = Modifier.weight(1f)) {
                // General Info Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                ) {
                    Column(modifier = Modifier.padding(32.dp)) {
                        Text("Informasi Umum", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
                        Spacer(modifier = Modifier.height(24.dp))
                        
                        Text("JUDUL UJIAN", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF94A3B8), letterSpacing = 1.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        BuilderTextField(value = title, onValueChange = { title = it }, placeholder = "mis. Ujian Tengah Semester")
                        
                        Spacer(modifier = Modifier.height(20.dp))
                        
                        Text("DESKRIPSI", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF94A3B8), letterSpacing = 1.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        BuilderTextField(
                            value = description, 
                            onValueChange = { description = it }, 
                            placeholder = "Ringkasan singkat...", 
                            singleLine = false, 
                            modifier = Modifier.fillMaxWidth().height(100.dp)
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))

                // Parameters Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                ) {
                    Column(modifier = Modifier.padding(32.dp)) {
                        Text("Parameter Ujian", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
                        Spacer(modifier = Modifier.height(24.dp))
                        
                        Text("BATAS WAKTU (MENIT)", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF94A3B8), letterSpacing = 1.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        BuilderTextField(value = duration, onValueChange = { duration = it }, placeholder = "60")
                        
                        Spacer(modifier = Modifier.height(20.dp))
                        
                        Text("TOKEN AKSES", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF94A3B8), letterSpacing = 1.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        BuilderTextField(value = token, onValueChange = { token = it }, placeholder = "Ketik token...")
                    }
                }
            }

            // RIGHT QUESTION BANK COLUMN
            Column(modifier = Modifier.weight(1.8f)) {
                if (questions.isEmpty()) {
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        color = Color(0xFFF8FAFC),
                        border = BorderStroke(1.dp, Color(0xFFE2E8F0))
                    ) {
                        Column(
                            modifier = Modifier.padding(64.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(Icons.Default.PostAdd, contentDescription = null, tint = Color(0xFF94A3B8), modifier = Modifier.size(48.dp))
                            Spacer(modifier = Modifier.height(16.dp))
                            Text("Belum Ada Soal Ujian", color = Color(0xFF0F172A), fontSize = 16.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Impor dari PDF atau tambahkan secara manual.", color = Color(0xFF64748B), fontSize = 13.sp)
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                }
                
                questions.forEachIndexed { index, question ->
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                    ) {
                        Column(modifier = Modifier.padding(32.dp)) {
                            // Question Header
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Surface(color = Color(0xFFE2E8F0), shape = RoundedCornerShape(6.dp)) {
                                        Text("Soal ${index + 1}", modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp), fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color(0xFF0F172A))
                                    }
                                    Spacer(modifier = Modifier.width(16.dp))
                                    Text("Pilihan Ganda", fontSize = 14.sp, color = Color(0xFF496E96), fontWeight = FontWeight.SemiBold)
                                }
                                IconButton(onClick = {
                                    val newList = questions.toMutableList()
                                    newList.removeAt(index)
                                    questions = newList
                                }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Hapus", tint = Color(0xFFEF4444), modifier = Modifier.size(20.dp))
                                }
                            }
                            
                            Spacer(modifier = Modifier.height(20.dp))
                            
                            // Question Input
                            OutlinedTextField(
                                value = question.text,
                                onValueChange = { v -> val l = questions.toMutableList(); l[index] = question.copy(text = v); questions = l },
                                modifier = Modifier.fillMaxWidth(),
                                placeholder = { Text("Ketikkan pertanyaan di sini...", color = Color(0xFF94A3B8)) },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color(0xFF496E96), 
                                    unfocusedBorderColor = Color.Transparent, 
                                    unfocusedContainerColor = Color.Transparent,
                                    focusedContainerColor = Color.Transparent
                                ),
                                textStyle = androidx.compose.ui.text.TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium, color = Color(0xFF0F172A))
                            )
                            
                            Spacer(modifier = Modifier.height(24.dp))
                            
                            // Options
                            val optionsList = listOf(
                                Pair("A", question.optionA), Pair("B", question.optionB), Pair("C", question.optionC), Pair("D", question.optionD)
                            )
                            
                            optionsList.forEach { (optLetter, optValue) ->
                                val isSelected = question.correctAnswer == optLetter
                                Surface(
                                    modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                                    shape = RoundedCornerShape(10.dp),
                                    color = if (isSelected) Color(0xFFF1F5F9) else Color.White,
                                    border = BorderStroke(1.dp, if (isSelected) Color(0xFF496E96) else Color(0xFFE2E8F0))
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        RadioButton(
                                            selected = isSelected,
                                            onClick = {
                                                val l = questions.toMutableList()
                                                l[index] = question.copy(correctAnswer = optLetter)
                                                questions = l
                                            },
                                            colors = RadioButtonDefaults.colors(selectedColor = Color(0xFF496E96))
                                        )
                                        Spacer(modifier = Modifier.width(12.dp))
                                        Surface(color = if (isSelected) Color(0xFF496E96) else Color(0xFFF1F5F9), shape = RoundedCornerShape(6.dp)) {
                                            Text(optLetter, modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp), fontWeight = FontWeight.Bold, fontSize = 12.sp, color = if (isSelected) Color.White else Color(0xFF64748B))
                                        }
                                        Spacer(modifier = Modifier.width(16.dp))
                                        OutlinedTextField(
                                            value = optValue,
                                            onValueChange = { v -> 
                                                val l = questions.toMutableList()
                                                when (optLetter) {
                                                    "A" -> l[index] = question.copy(optionA = v)
                                                    "B" -> l[index] = question.copy(optionB = v)
                                                    "C" -> l[index] = question.copy(optionC = v)
                                                    "D" -> l[index] = question.copy(optionD = v)
                                                }
                                                questions = l 
                                            },
                                            modifier = Modifier.weight(1f),
                                            colors = OutlinedTextFieldDefaults.colors(
                                                focusedBorderColor = Color.Transparent, 
                                                unfocusedBorderColor = Color.Transparent, 
                                                unfocusedContainerColor = Color.Transparent,
                                                focusedContainerColor = Color.Transparent
                                            ),
                                            singleLine = true,
                                            placeholder = { Text("Pilihan $optLetter", color = Color(0xFF94A3B8), fontSize = 14.sp) },
                                            textStyle = androidx.compose.ui.text.TextStyle(fontSize = 14.sp, color = Color(0xFF334155))
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // Add New Question Button
                OutlinedButton(
                    onClick = {
                        val newList = questions.toMutableList()
                        newList.add(Question(text = "", optionA = "", optionB = "", optionC = "", optionD = "", correctAnswer = ""))
                        questions = newList
                    },
                    modifier = Modifier.fillMaxWidth().height(60.dp),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(2.dp, Color(0xFFE2E8F0)),
                    colors = ButtonDefaults.outlinedButtonColors(containerColor = Color(0xFFF8FAFC), contentColor = Color(0xFF496E96))
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Tambah Pertanyaan Baru", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                }
                
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}

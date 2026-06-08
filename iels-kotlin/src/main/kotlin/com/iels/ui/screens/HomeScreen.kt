package com.iels.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iels.services.ExamService
import com.iels.ui.components.TopNavigationBar
import com.iels.ui.navigation.NavController
import com.iels.ui.navigation.Screen
import com.iels.services.SessionManager
import com.iels.models.StartSessionDto
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    var token by remember { mutableStateOf("") }
    var errorMsg by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    
    val examService = remember { ExamService() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = { TopNavigationBar() }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFF8FAFC)),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier.width(480.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(1.dp, Color(0xFFF1F5F9)),
                elevation = CardDefaults.cardElevation(0.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(48.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                Text(
                    "Iels CBT",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF0F172A),
                    letterSpacing = (-1).sp
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    "Masukkan token ujian yang diberikan oleh instruktur Anda untuk memulai sesi ujian yang aman.",
                    fontSize = 14.sp,
                    color = Color(0xFF64748B),
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(32.dp))
                
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "TOKEN UJIAN",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF64748B),
                        letterSpacing = 0.5.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = token,
                        onValueChange = { token = it },
                        modifier = Modifier.fillMaxWidth().height(52.dp),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color(0xFFF1F5F9),
                            unfocusedContainerColor = Color(0xFFF8FAFC),
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            errorIndicatorColor = Color.Transparent,
                            cursorColor = Color(0xFF1E293B)
                        ),
                        textStyle = androidx.compose.ui.text.TextStyle(fontSize = 15.sp, color = Color(0xFF0F172A), fontWeight = FontWeight.Medium)
                    )
                }
                
                if (errorMsg != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(errorMsg!!, color = Color(0xFFDC2626), fontSize = 12.sp)
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Button(
                    onClick = {
                        if (token.isBlank()) {
                            errorMsg = "Token tidak boleh kosong"
                            return@Button
                        }
                        
                        isLoading = true
                        errorMsg = null
                        
                        coroutineScope.launch {
                            val exam = examService.getExamByToken(token)
                            isLoading = false
                            if (exam != null) {
                                val currentUser = SessionManager.currentUser
                                if (currentUser != null && exam.id != null) {
                                    val startDto = StartSessionDto(userId = currentUser.id, examId = exam.id)
                                    examService.startSession(startDto)
                                }
                                NavController.startExam(token)
                            } else {
                                errorMsg = "Token tidak valid atau ujian tidak ditemukan"
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF3B82F6),
                        contentColor = Color.White,
                        disabledContainerColor = Color(0xFF93C5FD)
                    ),
                    enabled = !isLoading
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp), strokeWidth = 2.dp)
                    } else {
                        Text("Mulai Ujian", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    }
                }
            }
        }
    }
    }
}

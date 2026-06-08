package com.iels.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iels.models.Exam
import com.iels.models.ExamSubmission
import com.iels.services.ExamService
import com.iels.services.SessionManager
import com.iels.ui.navigation.NavController
import com.iels.ui.navigation.Screen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SecureExamScreen() {
    val examService = remember { ExamService() }
    val coroutineScope = rememberCoroutineScope()
    
    var exam by remember { mutableStateOf<Exam?>(null) }
    var currentQuestionIndex by remember { mutableStateOf(0) }
    var answers by remember { mutableStateOf<Map<Int, String>>(emptyMap()) }
    var cheatCount by remember { mutableStateOf(0) }
    
    var timeLeftSeconds by remember { mutableStateOf(0) }
    var isSubmitted by remember { mutableStateOf(false) }
    var finalScore by remember { mutableStateOf<Int?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val token = NavController.activeExamToken

    LaunchedEffect(token) {
        if (token != null) {
            val fetchedExam = examService.getExamByToken(token)
            if (fetchedExam != null) {
                exam = fetchedExam
                timeLeftSeconds = fetchedExam.durationMinutes * 60
            } else {
                errorMessage = "Gagal memuat soal ujian. Token tidak valid."
            }
        }
    }

    LaunchedEffect(timeLeftSeconds, isSubmitted) {
        if (!isSubmitted && timeLeftSeconds > 0) {
            delay(1000L)
            timeLeftSeconds--
            if (timeLeftSeconds <= 0) {
                // Auto submit when time runs out
                submitExam(exam, answers, cheatCount, examService) { score ->
                    finalScore = score
                    isSubmitted = true
                }
            }
        }
    }

    // Dummy Focus Tracking (In real production Desktop App, we use Window Event Listeners)
    // For this prototype, we'll simulate focus tracking logic.
    DisposableEffect(Unit) {
        // Here we would attach a java.awt.event.WindowFocusListener to the current window
        // window.addWindowFocusListener(object : WindowAdapter() {
        //     override fun windowLostFocus(e: WindowEvent?) { cheatCount++ }
        // })
        onDispose {
            // cleanup
        }
    }

    if (errorMessage != null) {
        Box(modifier = Modifier.fillMaxSize().background(Color.White), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(errorMessage!!, color = Color.Red, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { NavController.navigateTo(Screen.Home) }) {
                    Text("Kembali ke Beranda")
                }
            }
        }
        return
    }

    if (exam == null) {
        Box(modifier = Modifier.fillMaxSize().background(Color.White), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    if (isSubmitted) {
        Box(modifier = Modifier.fillMaxSize().background(Color.White), contentAlignment = Alignment.Center) {
            Card(
                modifier = Modifier.width(400.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Ujian Selesai!", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Nilai Anda:", fontSize = 16.sp)
                    Text("${finalScore ?: 0}", fontSize = 48.sp, fontWeight = FontWeight.Bold, color = Color(0xFF16A34A))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Peringatan Kecurangan Tercatat: $cheatCount", color = Color.Red)
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(onClick = { 
                        NavController.activeExamToken = null
                        NavController.navigateTo(Screen.Home) 
                    }) {
                        Text("Kembali ke Beranda")
                    }
                }
            }
        }
        return
    }

    // The Main Exam View (Fullscreen assumed handled in Main.kt)
    Scaffold(
        topBar = {
            Surface(color = Color(0xFF1E293B), modifier = Modifier.fillMaxWidth().height(64.dp)) {
                Row(
                    modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Iels Secure CBT - ${exam?.title}", color = Color.White, fontWeight = FontWeight.Bold)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if (cheatCount > 0) {
                            Surface(color = Color.Red, shape = RoundedCornerShape(4.dp)) {
                                Text("! PERINGATAN KECURANGAN: $cheatCount", color = Color.White, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp), fontWeight = FontWeight.Bold)
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                        }
                        val mins = timeLeftSeconds / 60
                        val secs = timeLeftSeconds % 60
                        Text(String.format("Waktu Tersisa: %02d:%02d", mins, secs), color = Color.Yellow, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    }
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).background(Color(0xFFF1F5F9))
        ) {
            Row(modifier = Modifier.fillMaxSize()) {
                // Left Panel: Navigation Grid
                Surface(
                    modifier = Modifier.width(250.dp).fillMaxHeight(),
                    color = Color.White,
                    shadowElevation = 2.dp
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Navigasi Soal", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Spacer(modifier = Modifier.height(16.dp))
                        // Assuming simple list for now
                        exam?.questions?.forEachIndexed { index, question ->
                            val isAnswered = answers.containsKey(question.id)
                            val isCurrent = currentQuestionIndex == index
                            
                            Button(
                                onClick = { currentQuestionIndex = index },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (isCurrent) Color(0xFF6366F1) else if (isAnswered) Color(0xFF22C55E) else Color(0xFFE2E8F0),
                                    contentColor = if (isCurrent || isAnswered) Color.White else Color.Black
                                ),
                                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                            ) {
                                Text("Soal ${index + 1}")
                            }
                        }
                        
                        Spacer(modifier = Modifier.weight(1f))
                        
                        Button(
                            onClick = {
                                coroutineScope.launch {
                                    submitExam(exam, answers, cheatCount, examService) { score ->
                                        finalScore = score
                                        isSubmitted = true
                                    }
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDC2626)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Selesai & Kumpulkan", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }
                }
                
                // Right Panel: Question Content
                Box(modifier = Modifier.weight(1f).padding(32.dp)) {
                    val question = exam?.questions?.getOrNull(currentQuestionIndex)
                    if (question != null) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(2.dp)
                        ) {
                            Column(modifier = Modifier.padding(32.dp)) {
                                Text("Soal ${currentQuestionIndex + 1}", color = Color.Gray, fontWeight = FontWeight.Bold)
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(question.text, fontSize = 20.sp, fontWeight = FontWeight.Medium, color = Color(0xFF1E293B))
                                Spacer(modifier = Modifier.height(32.dp))
                                
                                val options = listOf("A" to question.optionA, "B" to question.optionB, "C" to question.optionC, "D" to question.optionD)
                                val currentAnswer = answers[question.id]
                                
                                options.forEach { (key, text) ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 8.dp)
                                            .background(if (currentAnswer == key) Color(0xFFEEF2FF) else Color(0xFFF8FAFC), RoundedCornerShape(8.dp))
                                            .selectable(
                                                selected = (currentAnswer == key),
                                                onClick = {
                                                    question.id?.let { qId ->
                                                        val newAnswers = answers.toMutableMap()
                                                        newAnswers[qId] = key
                                                        answers = newAnswers
                                                    }
                                                }
                                            )
                                            .padding(16.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        RadioButton(
                                            selected = (currentAnswer == key),
                                            onClick = null
                                        )
                                        Spacer(modifier = Modifier.width(16.dp))
                                        Text(text, fontSize = 16.sp)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

private suspend fun submitExam(
    exam: Exam?,
    answers: Map<Int, String>,
    cheatCount: Int,
    examService: ExamService,
    onSuccess: (Int) -> Unit
) {
    if (exam == null) return
    val user = SessionManager.currentUser ?: return
    
    val submission = ExamSubmission(
        userId = user.id,
        examId = exam.id!!,
        answers = answers,
        cheatCount = cheatCount
    )
    
    val result = examService.submitExam(submission)
    if (result != null) {
        onSuccess(result.score)
    }
}

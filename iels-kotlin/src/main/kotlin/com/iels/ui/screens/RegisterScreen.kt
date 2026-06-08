package com.iels.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iels.services.AuthService
import com.iels.services.SessionManager
import com.iels.ui.navigation.NavController
import com.iels.ui.navigation.Screen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen() {
    var name by remember { mutableStateOf("") }
    var nim by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }

    val authService = remember { AuthService() }
    val coroutineScope = rememberCoroutineScope()

    Row(modifier = Modifier.fillMaxSize()) {
        // LEFT PANE (Reusing layout from LoginScreen)
        Box(
            modifier = Modifier
                .weight(0.4f)
                .fillMaxHeight()
                .background(Color(0xFFD6E4F7)) // Light IELS Blue
        ) {
            Column(modifier = Modifier.padding(64.dp)) {
                Text(
                    text = "SEB CBT UNSRAT IELS",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF0F172A),
                    lineHeight = 36.sp
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Platform Ujian Digital Terintegrasi khusus untuk program studi Teknik Informatika, Universitas Sam Ratulangi. Menyediakan lingkungan ujian yang aman, terpercaya, dan profesional.",
                    color = Color(0xFF475569),
                    fontSize = 15.sp,
                    lineHeight = 24.sp
                )

                Spacer(modifier = Modifier.height(56.dp))

                LogoItem("logo unsrat.png", "Universitas Sam Ratulangi", "Institusi Pendidikan Tinggi Terkemuka")
                Spacer(modifier = Modifier.height(36.dp))
                LogoItem("Fatek.png", "Fakultas Teknik", "Inovasi dan Teknologi untuk Masa Depan")
                Spacer(modifier = Modifier.height(36.dp))
                LogoItem("IELS Logo 3.png", "IELS Platform", "Informatics Engineering Learning System")
            }
        }

        // RIGHT PANE (Registration Form)
        Box(
            modifier = Modifier
                .weight(0.6f)
                .fillMaxHeight()
                .background(Color.White)
        ) {
            // Back Button
            IconButton(
                onClick = { NavController.navigateTo(Screen.Landing) },
                modifier = Modifier.align(Alignment.TopStart).padding(32.dp)
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Kembali ke Beranda", tint = Color(0xFF64748B))
            }

            Column(
                modifier = Modifier.width(460.dp).align(Alignment.Center)
            ) {

                Text(
                    text = "Buat Akun Siswa",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF0F172A)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Daftarkan diri Anda untuk mengakses platform ujian",
                    color = Color(0xFF64748B),
                    fontSize = 15.sp
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Name Input
                Row {
                    Text("Nama Lengkap ", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF0F172A))
                    Text("*", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFFDC2626))
                }
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    placeholder = { Text("Masukkan Nama Lengkap", color = Color(0xFF94A3B8), fontSize = 14.sp) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    enabled = !isLoading,
                    shape = RoundedCornerShape(8.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFF1F5F9),
                        unfocusedContainerColor = Color(0xFFF8FAFC),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        cursorColor = Color(0xFF0F172A)
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // NIM Input
                Row {
                    Text("NIM ", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF0F172A))
                    Text("*", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFFDC2626))
                }
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = nim,
                    onValueChange = { nim = it },
                    placeholder = { Text("Masukkan Nomor Induk Mahasiswa", color = Color(0xFF94A3B8), fontSize = 14.sp) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    enabled = !isLoading,
                    shape = RoundedCornerShape(8.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFF1F5F9),
                        unfocusedContainerColor = Color(0xFFF8FAFC),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        cursorColor = Color(0xFF0F172A)
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Email Input
                Row {
                    Text("Email ", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF0F172A))
                    Text("*", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFFDC2626))
                }
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = { Text("Masukkan Alamat Email", color = Color(0xFF94A3B8), fontSize = 14.sp) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    enabled = !isLoading,
                    shape = RoundedCornerShape(8.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFF1F5F9),
                        unfocusedContainerColor = Color(0xFFF8FAFC),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        cursorColor = Color(0xFF0F172A)
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Password Input
                Row {
                    Text("Password ", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF0F172A))
                    Text("*", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFFDC2626))
                }
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = { Text("********", color = Color(0xFF94A3B8), fontSize = 14.sp) },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                imageVector = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                contentDescription = "Toggle Password",
                                tint = Color(0xFF94A3B8),
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    },
                    singleLine = true,
                    enabled = !isLoading,
                    shape = RoundedCornerShape(8.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFF1F5F9),
                        unfocusedContainerColor = Color(0xFFF8FAFC),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        cursorColor = Color(0xFF0F172A)
                    )
                )

                if (error != null) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = error!!, color = Color(0xFFDC2626), fontSize = 13.sp, fontWeight = FontWeight.Medium)
                }

                Spacer(modifier = Modifier.height(32.dp))

                // REGISTER BUTTON
                Button(
                    onClick = {
                        if (name.isBlank() || nim.isBlank() || email.isBlank() || password.isBlank()) {
                            error = "Semua field bertanda * harus diisi"
                            return@Button
                        }
                        
                        error = null
                        isLoading = true
                        
                        coroutineScope.launch {
                            try {
                                val user = authService.register(email, password, name, nim) // role = "student" defaults in AuthService
                                if (user != null) {
                                    // Registration successful, log them in automatically
                                    SessionManager.setLocalUser(user)
                                    println("✓ Registration successful: ${user.email}")
                                    NavController.navigateTo(Screen.Home)
                                } else {
                                    error = "Pendaftaran gagal. Email/NIM mungkin sudah terdaftar."
                                }
                            } catch (e: Exception) {
                                println("Registration error: ${e.message}")
                                error = "Terjadi kesalahan sistem saat mendaftar"
                            } finally {
                                isLoading = false
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF3B82F6),
                        contentColor = Color.White
                    ),
                    enabled = !isLoading
                ) { 
                    if (isLoading) {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White, strokeWidth = 2.dp)
                    } else {
                        Text("DAFTAR SEKARANG", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 15.sp, letterSpacing = 1.sp)
                    }
                }

                // Footer
                Spacer(modifier = Modifier.height(32.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                    Row {
                        Text("Sudah punya akun? ", color = Color(0xFF64748B), fontSize = 13.sp)
                        Text("Login di sini", color = Color(0xFF3B82F6), fontWeight = FontWeight.Bold, fontSize = 13.sp, modifier = Modifier.clickable {
                            NavController.navigateTo(Screen.Login)
                        })
                    }
                    
                    Spacer(modifier = Modifier.height(32.dp))
                    Image(
                        painter = painterResource("IELS Logo 3.png"),
                        contentDescription = "Logo IELS",
                        modifier = Modifier.height(40.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Powered by IELS", color = Color(0xFF94A3B8), fontSize = 12.sp, fontWeight = FontWeight.Medium)
                }
            }
        }
    }
}

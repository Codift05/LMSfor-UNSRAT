package com.iels.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
import java.util.prefs.Preferences

@Composable
fun LogoItem(imageRes: String, title: String, subtitle: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.6f)),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color.White),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically, 
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Image(
                painter = painterResource(imageRes),
                contentDescription = title,
                modifier = Modifier.size(52.dp)
            )
            Spacer(modifier = Modifier.width(20.dp))
            Column {
                Text(title, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A), fontSize = 15.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(subtitle, color = Color(0xFF475569), fontSize = 13.sp, lineHeight = 18.sp)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen() {
    val prefs = remember { Preferences.userRoot().node("iels-cbt-login") }
    
    var identifier by remember { mutableStateOf(prefs.get("saved_identifier", "")) }
    var password by remember { mutableStateOf(prefs.get("saved_password", "")) }
    var rememberMe by remember { mutableStateOf(prefs.getBoolean("remember_me", false)) }
    var error by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }
    var showAdminDialog by remember { mutableStateOf(false) }

    val authService = remember { AuthService() }
    val coroutineScope = rememberCoroutineScope()

    Row(modifier = Modifier.fillMaxSize()) {
        // LEFT PANE
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

        // RIGHT PANE
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
                    text = "Log In Akun",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF0F172A)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    Text("Hi, Selamat Datang ", color = Color(0xFF64748B), fontSize = 15.sp)
                    Text("#Ielsers", color = Color(0xFF496E96), fontWeight = FontWeight.Bold, fontSize = 15.sp)
                }

                Spacer(modifier = Modifier.height(40.dp))

                // Email Input
                Row {
                    Text("Email atau NIM ", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF0F172A))
                    Text("*", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFFDC2626))
                }
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = identifier,
                    onValueChange = { identifier = it },
                    placeholder = { Text("Masukkan Email atau NIM", color = Color(0xFF94A3B8), fontSize = 14.sp) },
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

                Spacer(modifier = Modifier.height(24.dp))

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

                Spacer(modifier = Modifier.height(16.dp))

                // Remember Me & Forgot Password
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = rememberMe,
                            onCheckedChange = { rememberMe = it },
                            colors = CheckboxDefaults.colors(checkedColor = Color(0xFF3B82F6))
                        )
                        Text("Ingat Saya", color = Color(0xFF64748B), fontSize = 13.sp)
                    }
                    Text(
                        "Lupa Password",
                        color = Color(0xFF3B82F6),
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        modifier = Modifier.clickable { showAdminDialog = true }
                    )
                }

                if (error != null) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = error!!, color = Color(0xFFDC2626), fontSize = 13.sp, fontWeight = FontWeight.Medium)
                }

                Spacer(modifier = Modifier.height(32.dp))

                // LOGIN BUTTON
                Button(
                    onClick = {
                        if (identifier.isEmpty() || password.isEmpty()) {
                            error = "Email/NIM dan Password harus diisi"
                            return@Button
                        }
                        
                        error = null
                        isLoading = true
                        
                        coroutineScope.launch {
                            try {
                                val user = authService.login(identifier, password)
                                if (user != null) {
                                    if (rememberMe) {
                                        prefs.put("saved_identifier", identifier)
                                        prefs.put("saved_password", password)
                                        prefs.putBoolean("remember_me", true)
                                    } else {
                                        prefs.remove("saved_identifier")
                                        prefs.remove("saved_password")
                                        prefs.putBoolean("remember_me", false)
                                    }
                                    SessionManager.setLocalUser(user)
                                    println("✓ Login successful: \${user.email}")
                                    when (user.role) {
                                        "admin" -> NavController.navigateTo(Screen.AdminOverview)
                                        "instructor" -> NavController.navigateTo(Screen.AdminOverview)
                                        else -> NavController.navigateTo(Screen.Home)
                                    }
                                } else {
                                    error = "NIM/Email atau Password salah"
                                }
                            } catch (e: Exception) {
                                println("Login error: \${e.message}")
                                error = "Terjadi kesalahan saat login"
                            } finally {
                                isLoading = false
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    shape = androidx.compose.foundation.shape.CircleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF3B82F6),
                        contentColor = Color.White
                    ),
                    enabled = !isLoading
                ) { 
                    if (isLoading) {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White, strokeWidth = 2.dp)
                    } else {
                        Text("LOGIN", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 15.sp, letterSpacing = 1.sp)
                    }
                }

                // Footer
                Spacer(modifier = Modifier.height(32.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                    Row {
                        Text("Belum Punya Akun? ", color = Color(0xFF64748B), fontSize = 13.sp)
                        Text("Daftar", color = Color(0xFF3B82F6), fontWeight = FontWeight.Bold, fontSize = 13.sp, modifier = Modifier.clickable { NavController.navigateTo(Screen.Register) })
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        Text("Tidak dapat mengakses akun? ", color = Color(0xFF64748B), fontSize = 13.sp)
                        Text("Pemulihan akun", color = Color(0xFF3B82F6), fontWeight = FontWeight.Bold, fontSize = 13.sp, modifier = Modifier.clickable { showAdminDialog = true })
                    }
                    
                    Spacer(modifier = Modifier.height(48.dp))
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

    if (showAdminDialog) {
        AlertDialog(
            onDismissRequest = { showAdminDialog = false },
            title = { Text("Pemberitahuan Sistem", fontWeight = FontWeight.Bold, color = Color(0xFF0F172A)) },
            text = { Text("Demi keamanan platform IELS CBT, proses pendaftaran, pemulihan akun, maupun lupa password hanya dapat diproses oleh Administrator. Silakan hubungi admin di admin@iels.com atau kunjungi bagian Akademik Fakultas Teknik Universitas Sam Ratulangi.", color = Color(0xFF475569)) },
            confirmButton = {
                TextButton(onClick = { showAdminDialog = false }) {
                    Text("Mengerti", color = Color(0xFF3B82F6), fontWeight = FontWeight.Bold)
                }
            },
            containerColor = Color.White
        )
    }
}

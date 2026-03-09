package com.edudesk.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edudesk.services.SessionManager
import com.edudesk.services.UserService
import com.edudesk.ui.components.TopNavigationBar
import com.edudesk.ui.navigation.NavController
import com.edudesk.ui.navigation.Screen
import com.edudesk.ui.theme.IelsMagenta
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen() {
    val currentUser = SessionManager.currentUser
    val userService = remember { UserService() }
    val coroutineScope = rememberCoroutineScope()

    var name by remember { mutableStateOf(currentUser?.name ?: "") }
    var nimEmail by remember {
        mutableStateOf(currentUser?.nim ?: "")
    } // Since NIM is used as identifier
    var email by remember { mutableStateOf(currentUser?.email ?: "") }
    var password by remember { mutableStateOf("") }

    var saveStatus by remember { mutableStateOf<String?>(null) }
    var isError by remember { mutableStateOf(false) }

    Scaffold(topBar = { TopNavigationBar() }) { padding ->
        Column(
                modifier =
                        Modifier.fillMaxSize()
                                .padding(padding)
                                .background(Color(0xFFF8FAFC))
                                .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(48.dp))

            Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(2.dp),
                    modifier = Modifier.width(600.dp).padding(16.dp)
            ) {
                Column(
                        modifier = Modifier.padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        IconButton(
                                onClick = { NavController.navigateTo(Screen.Home) },
                                modifier = Modifier.align(Alignment.CenterStart)
                        ) { Icon(Icons.Default.ArrowBack, contentDescription = "Kembali") }
                        Text(
                                "Pengaturan Profil",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1E293B),
                                modifier = Modifier.align(Alignment.Center)
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    // Avatar Section
                    Surface(
                            modifier = Modifier.size(100.dp),
                            shape = CircleShape,
                            color = IelsMagenta.copy(alpha = 0.1f)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                    currentUser?.name?.take(2)?.uppercase() ?: "US",
                                    color = IelsMagenta,
                                    fontSize = 36.sp,
                                    fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    TextButton(onClick = { /* TODO: Implement photo upload */}) {
                        Text(
                                "Ubah Foto Profil",
                                color = IelsMagenta,
                                fontWeight = FontWeight.Medium
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    OutlinedTextField(
                            value = name,
                            onValueChange = { name = it },
                            label = { Text("Nama Lengkap") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                            value = nimEmail,
                            onValueChange = { nimEmail = it },
                            label = { Text("NIM/NIP") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            label = { Text("Email") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                            value = password,
                            onValueChange = { password = it },
                            label = { Text("Password Baru (Opsional)") },
                            modifier = Modifier.fillMaxWidth(),
                            visualTransformation = PasswordVisualTransformation(),
                            singleLine = true
                    )

                    if (saveStatus != null) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                                saveStatus!!,
                                color = if (isError) Color.Red else Color(0xFF16A34A),
                                fontSize = 14.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    Button(
                            onClick = {
                                if (currentUser != null) {
                                    coroutineScope.launch {
                                        val success =
                                                userService.updateProfile(
                                                        userId = currentUser.id.value,
                                                        name = name,
                                                        nimEmail = nimEmail,
                                                        email = email,
                                                        password =
                                                                password.takeIf { it.isNotBlank() }
                                                )

                                        if (success) {
                                            // Update the local session data so UI updates
                                            // immediately
                                            SessionManager.currentUser?.name = name
                                            SessionManager.currentUser?.nim = nimEmail
                                            SessionManager.currentUser?.email = email
                                            // Force recomposition
                                            SessionManager.currentUser = SessionManager.currentUser

                                            saveStatus = "Profil berhasil diperbarui!"
                                            isError = false
                                            password = "" // clear password field after sumbit
                                        } else {
                                            saveStatus = "Gagal memperbarui profil."
                                            isError = true
                                        }
                                    }
                                } else {
                                    saveStatus = "Tidak ada pengguna yang login."
                                    isError = true
                                }
                            },
                            modifier = Modifier.fillMaxWidth().height(48.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = IelsMagenta)
                    ) { Text("Simpan Perubahan", fontWeight = FontWeight.Bold, fontSize = 16.sp) }
                }
            }

            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}

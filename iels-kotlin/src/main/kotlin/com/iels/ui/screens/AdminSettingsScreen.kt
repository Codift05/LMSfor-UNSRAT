package com.iels.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.shadow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iels.services.SessionManager
import com.iels.services.UserService
import com.iels.ui.components.AdminLayout
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
private fun SettingsTextField(value: String, onValueChange: (String) -> Unit, label: String, isPassword: Boolean = false) {
    Column {
        Text(
            text = label.uppercase(),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF64748B),
            letterSpacing = 0.5.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth().height(52.dp),
            singleLine = true,
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
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
}

@Composable
fun AdminSettingsScreen() {
    val userService = remember { UserService() }
    val coroutineScope = rememberCoroutineScope()
    
    val currentUser = SessionManager.currentUser
    
    // Profile Edit State
    var editName by remember { mutableStateOf(currentUser?.name ?: "") }
    var editNim by remember { mutableStateOf(currentUser?.nim ?: "") }
    var editEmail by remember { mutableStateOf(currentUser?.email ?: "") }
    
    var isSavingProfile by remember { mutableStateOf(false) }
    var profileStatusMessage by remember { mutableStateOf<String?>(null) }
    var isProfileError by remember { mutableStateOf(false) }

    // Password Update State
    var newPassword by remember { mutableStateOf("") }
    
    var isSavingPassword by remember { mutableStateOf(false) }
    var passwordStatusMessage by remember { mutableStateOf<String?>(null) }
    var isPasswordError by remember { mutableStateOf(false) }
    
    AdminLayout(activeMenu = "Pengaturan") {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 48.dp, vertical = 32.dp)
        ) {
            // HEADER
            Text("Pengaturan", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
            Spacer(modifier = Modifier.height(8.dp))
            Text("Kelola profil, preferensi akun, dan keamanan Anda.", fontSize = 14.sp, color = Color(0xFF64748B))

            Spacer(modifier = Modifier.height(40.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(32.dp)) {
                
                // Account Info Card
                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(20.dp),
                    border = BorderStroke(1.dp, Color(0xFFF1F5F9)),
                    elevation = CardDefaults.cardElevation(0.dp)
                ) {
                    Column(modifier = Modifier.padding(40.dp)) {
                        Text("Informasi Profil", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
                        Spacer(modifier = Modifier.height(32.dp))
                        
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                shape = CircleShape,
                                border = BorderStroke(4.dp, Color(0xFFF1F5F9)),
                                modifier = Modifier.size(72.dp)
                            ) {
                                Surface(
                                    shape = CircleShape,
                                    color = Color(0xFFF8FAFC),
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Icon(Icons.Default.Person, contentDescription = null, tint = Color(0xFF94A3B8), modifier = Modifier.padding(16.dp))
                                }
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text(currentUser?.name ?: "Nama Pengguna", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
                                Spacer(modifier = Modifier.height(4.dp))
                                Surface(
                                    color = if (currentUser?.role == "admin") Color(0xFFFEE2E2) else Color(0xFFE0E7FF),
                                    shape = RoundedCornerShape(6.dp)
                                ) {
                                    Text(
                                        text = (currentUser?.role ?: "Role").uppercase(),
                                        color = if (currentUser?.role == "admin") Color(0xFFB91C1C) else Color(0xFF4338CA),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 11.sp,
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                        letterSpacing = 0.5.sp
                                    )
                                }
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(32.dp))
                        
                        SettingsTextField(value = editName, onValueChange = { editName = it }, label = "Nama Lengkap")
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        SettingsTextField(value = editEmail, onValueChange = { editEmail = it }, label = "Email")
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        SettingsTextField(value = editNim, onValueChange = { editNim = it }, label = "NIM / ID Pengguna")
                        Spacer(modifier = Modifier.height(24.dp))
                        
                        if (profileStatusMessage != null) {
                            Surface(
                                color = if (isProfileError) Color(0xFFFEE2E2) else Color(0xFFD1FAE5),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = if (isProfileError) Icons.Default.Warning else Icons.Default.CheckCircle,
                                        contentDescription = null,
                                        tint = if (isProfileError) Color(0xFFDC2626) else Color(0xFF059669),
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = profileStatusMessage!!,
                                        color = if (isProfileError) Color(0xFFDC2626) else Color(0xFF059669),
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                        }

                        Button(
                            onClick = {
                                if (editName.isNotBlank() && editEmail.isNotBlank() && editNim.isNotBlank() && currentUser != null) {
                                    isSavingProfile = true
                                    profileStatusMessage = null
                                    coroutineScope.launch {
                                        val success = userService.updateProfile(
                                            userId = currentUser.id,
                                            name = editName,
                                            nimEmail = editNim,
                                            email = editEmail,
                                            password = null // Do not update password here
                                        )
                                        if (success) {
                                            isProfileError = false
                                            profileStatusMessage = "Profil berhasil diperbarui!"
                                            // Update local session to reflect changes immediately
                                            SessionManager.currentUser = currentUser.copy(
                                                name = editName,
                                                nim = editNim,
                                                email = editEmail
                                            )
                                        } else {
                                            isProfileError = true
                                            profileStatusMessage = "Gagal memperbarui profil."
                                        }
                                        isSavingProfile = false
                                        
                                        delay(3000)
                                        profileStatusMessage = null
                                    }
                                }
                            },
                            enabled = !isSavingProfile && editName.isNotBlank() && editEmail.isNotBlank() && editNim.isNotBlank(),
                            modifier = Modifier.fillMaxWidth().height(48.dp),
                            shape = CircleShape,
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3B82F6), contentColor = Color.White, disabledContainerColor = Color(0xFF93C5FD))
                        ) {
                            Text(if (isSavingProfile) "Menyimpan..." else "Simpan Profil", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color.White)
                        }
                    }
                }

                // Security Card
                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(20.dp),
                    border = BorderStroke(1.dp, Color(0xFFF1F5F9)),
                    elevation = CardDefaults.cardElevation(0.dp)
                ) {
                    Column(modifier = Modifier.padding(40.dp)) {
                        Text("Keamanan & Sandi", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
                        Spacer(modifier = Modifier.height(32.dp))
                        
                        SettingsTextField(
                            value = newPassword, 
                            onValueChange = { newPassword = it },
                            label = "Kata Sandi Baru",
                            isPassword = true
                        )

                        Spacer(modifier = Modifier.height(24.dp))
                        
                        if (passwordStatusMessage != null) {
                            Surface(
                                color = if (isPasswordError) Color(0xFFFEE2E2) else Color(0xFFD1FAE5),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = if (isPasswordError) Icons.Default.Warning else Icons.Default.CheckCircle,
                                        contentDescription = null,
                                        tint = if (isPasswordError) Color(0xFFDC2626) else Color(0xFF059669),
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = passwordStatusMessage!!,
                                        color = if (isPasswordError) Color(0xFFDC2626) else Color(0xFF059669),
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                        }

                        Button(
                            onClick = {
                                if (newPassword.isNotBlank() && currentUser != null) {
                                    isSavingPassword = true
                                    passwordStatusMessage = null
                                    coroutineScope.launch {
                                        val success = userService.updateProfile(
                                            userId = currentUser.id,
                                            name = currentUser.name,
                                            nimEmail = currentUser.nim,
                                            email = currentUser.email,
                                            password = newPassword
                                        )
                                        if (success) {
                                            isPasswordError = false
                                            passwordStatusMessage = "Kata sandi berhasil diperbarui!"
                                            newPassword = ""
                                        } else {
                                            isPasswordError = true
                                            passwordStatusMessage = "Gagal memperbarui kata sandi."
                                        }
                                        isSavingPassword = false
                                        
                                        delay(3000)
                                        passwordStatusMessage = null
                                    }
                                }
                            },
                            enabled = !isSavingPassword && newPassword.isNotBlank(),
                            modifier = Modifier.fillMaxWidth().height(48.dp),
                            shape = CircleShape,
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3B82F6), contentColor = Color.White, disabledContainerColor = Color(0xFF93C5FD))
                        ) {
                            Text(if (isSavingPassword) "Menyimpan..." else "Simpan Kata Sandi Baru", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color.White)
                        }
                    }
                }
            }
        }
    }
}

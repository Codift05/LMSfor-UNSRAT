package com.edudesk.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edudesk.services.SessionManager
import com.edudesk.services.UserService
import com.edudesk.ui.components.TopNavigationBar
import com.edudesk.ui.navigation.NavController
import com.edudesk.ui.navigation.Screen
import kotlinx.coroutines.launch

private val IelsBlue = Color(0xFF496E96)
private val TextDark = Color(0xFF1E293B)
private val TextMuted = Color(0xFF64748B)
private val BorderColor = Color(0xFFDDE3EA)
private val BackgroundPage = Color(0xFFE2E8EF)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen() {
    val currentUser = SessionManager.currentUser
    val userService = remember { UserService() }
    val coroutineScope = rememberCoroutineScope()

    var name by remember { mutableStateOf(currentUser?.name ?: "") }
    var nimEmail by remember { mutableStateOf(currentUser?.nim ?: "") }
    var email by remember { mutableStateOf(currentUser?.email ?: "") }
    var password by remember { mutableStateOf("") }

    var saveStatus by remember { mutableStateOf<String?>(null) }
    var isError by remember { mutableStateOf(false) }

    val initials = currentUser?.name
        ?.split(" ")
        ?.filter { it.isNotEmpty() }
        ?.take(2)
        ?.map { it.first().uppercaseChar() }
        ?.joinToString("") ?: "US"

    val roleLabel = when (currentUser?.role) {
        "student"    -> "Mahasiswa"
        "instructor" -> "Dosen"
        "admin"      -> "Administrator"
        else         -> "Pengguna"
    }

    Scaffold(
        topBar = { TopNavigationBar() },
        containerColor = BackgroundPage
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(BackgroundPage)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 48.dp, vertical = 32.dp)
        ) {
            // Page Header
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = { NavController.navigateTo(Screen.Home) },
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Kembali",
                        tint = TextDark
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        "Pengaturan Profil",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDark
                    )
                    Text(
                        "Kelola informasi dan keamanan akunmu.",
                        fontSize = 13.sp,
                        color = TextMuted
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                verticalAlignment = Alignment.Top
            ) {
                // ── LEFT PANEL: Profile Summary ──────────────────────────────
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(0.dp),
                    border = BorderStroke(1.dp, BorderColor),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.width(260.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Avatar
                        Box(contentAlignment = Alignment.BottomEnd) {
                            Surface(
                                modifier = Modifier.size(88.dp),
                                shape = CircleShape,
                                color = IelsBlue
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        initials,
                                        color = Color.White,
                                        fontSize = 30.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                            Surface(
                                modifier = Modifier.size(28.dp),
                                shape = CircleShape,
                                color = Color(0xFFF1F5F9),
                                border = BorderStroke(1.5.dp, BorderColor)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        Icons.Default.CameraAlt,
                                        contentDescription = "Ubah foto",
                                        tint = IelsBlue,
                                        modifier = Modifier.size(14.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            currentUser?.name ?: "User",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = TextDark
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Surface(
                            color = IelsBlue.copy(alpha = 0.08f),
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Text(
                                roleLabel,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = IelsBlue
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))
                        HorizontalDivider(color = BorderColor)
                        Spacer(modifier = Modifier.height(16.dp))

                        // Info rows
                        ProfileInfoRow(
                            icon = Icons.Default.Person,
                            label = "NIM/NIP",
                            value = currentUser?.nim ?: "-"
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        ProfileInfoRow(
                            icon = Icons.Default.Email,
                            label = "Email",
                            value = currentUser?.email ?: "-"
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        ProfileInfoRow(
                            icon = Icons.Default.School,
                            label = "Program",
                            value = "Teknik Informatika"
                        )
                    }
                }

                // ── RIGHT PANEL: Edit Form ───────────────────────────────────
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    // Personal Info Card
                    ProfileSectionCard(title = "Informasi Pribadi") {
                        ProfileFormField(
                            label = "Nama Lengkap",
                            value = name,
                            onValueChange = { name = it },
                            leadingIcon = Icons.Default.Person
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        ProfileFormField(
                            label = "NIM / NIP",
                            value = nimEmail,
                            onValueChange = { nimEmail = it },
                            leadingIcon = Icons.Default.School
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        ProfileFormField(
                            label = "Alamat Email",
                            value = email,
                            onValueChange = { email = it },
                            leadingIcon = Icons.Default.Email
                        )
                    }

                    // Security Card
                    ProfileSectionCard(title = "Keamanan Akun") {
                        Text(
                            "Biarkan kosong jika tidak ingin mengubah kata sandi.",
                            fontSize = 12.sp,
                            color = TextMuted
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        ProfileFormField(
                            label = "Password Baru (Opsional)",
                            value = password,
                            onValueChange = { password = it },
                            leadingIcon = Icons.Default.Lock,
                            isPassword = true
                        )
                    }

                    // Status Message
                    if (saveStatus != null) {
                        Surface(
                            color = if (isError) Color(0xFFFFF5F5) else Color(0xFFF0FDF4),
                            shape = RoundedCornerShape(10.dp),
                            border = BorderStroke(
                                1.dp,
                                if (isError) Color(0xFFFFCDD2) else Color(0xFFBBF7D0)
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                saveStatus!!,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = if (isError) Color(0xFFB71C1C) else Color(0xFF15803D)
                            )
                        }
                    }

                    // Save Button
                    Button(
                        onClick = {
                            if (currentUser != null) {
                                coroutineScope.launch {
                                    val success = userService.updateProfile(
                                        userId = currentUser.id.value,
                                        name = name,
                                        nimEmail = nimEmail,
                                        email = email,
                                        password = password.takeIf { it.isNotBlank() }
                                    )

                                    if (success) {
                                        SessionManager.currentUser?.name = name
                                        SessionManager.currentUser?.nim = nimEmail
                                        SessionManager.currentUser?.email = email
                                        SessionManager.currentUser = SessionManager.currentUser

                                        saveStatus = "✓  Profil berhasil diperbarui!"
                                        isError = false
                                        password = ""
                                    } else {
                                        saveStatus = "Gagal memperbarui profil. Coba lagi."
                                        isError = true
                                    }
                                }
                            } else {
                                saveStatus = "Tidak ada pengguna yang login."
                                isError = true
                            }
                        },
                        modifier = Modifier
                            .align(Alignment.End)
                            .height(44.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = IelsBlue,
                            contentColor = Color.White
                        ),
                        contentPadding = PaddingValues(horizontal = 32.dp)
                    ) {
                        Text(
                            "Simpan Perubahan",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 15.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}

@Composable
private fun ProfileSectionCard(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(0.dp),
        border = BorderStroke(1.dp, BorderColor),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text(title, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = TextDark)
            Spacer(modifier = Modifier.height(18.dp))
            content()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProfileFormField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    leadingIcon: ImageVector,
    isPassword: Boolean = false
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = TextMuted
        )
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            singleLine = true,
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            leadingIcon = {
                Icon(
                    leadingIcon,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint = TextMuted
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = IelsBlue,
                unfocusedBorderColor = Color(0xFFE2E8F0),
                focusedLabelColor = IelsBlue,
                cursorColor = IelsBlue,
                unfocusedContainerColor = Color(0xFFFAFAFA),
                focusedContainerColor = Color.White
            )
        )
    }
}

@Composable
private fun ProfileInfoRow(
    icon: ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            icon,
            contentDescription = null,
            tint = IelsBlue,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(label, fontSize = 10.sp, color = TextMuted, fontWeight = FontWeight.Medium)
            Text(value, fontSize = 12.sp, color = TextDark, fontWeight = FontWeight.SemiBold)
        }
    }
}

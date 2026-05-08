package com.edudesk.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.edudesk.ui.components.TopNavigationBar
import com.edudesk.ui.theme.*

@Composable
fun ProfileFormField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    leadingIcon: ImageVector,
    isPassword: Boolean = false
) {
    var passwordVisible by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, fontSize = 13.sp, color = TextMuted) },
        leadingIcon = {
            Icon(
                imageVector = leadingIcon,
                contentDescription = null,
                tint = IelsBlue
            )
        },
        trailingIcon = if (isPassword) {
            {
                // Fallback: use Lock icon for both states if Visibility icon not available
                val icon = Icons.Filled.Lock
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = icon, contentDescription = null)
                }
            }
        } else null,
        visualTransformation = if (isPassword && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = IelsMagenta,
            unfocusedBorderColor = BorderColor,
            cursorColor = IelsMagenta
        )
    )
}

@Composable
fun ProfileScreen() {
    Scaffold(topBar = { TopNavigationBar() }) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundPage)
                .padding(padding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
                    .verticalScroll(rememberScrollState())
                    .padding(top = 48.dp, bottom = 48.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Avatar & Info
                Surface(
                    modifier = Modifier.size(104.dp),
                    shape = CircleShape,
                    color = IelsBlue
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = "Avatar",
                            tint = Color.White,
                            modifier = Modifier.size(56.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(18.dp))
                Text(
                    text = "Nama Pengguna", // TODO: ganti dengan currentUser?.name
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = TextDark
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "nim@email.com", // TODO: ganti dengan currentUser?.email
                    fontSize = 14.sp,
                    color = TextMuted
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        color = IelsBlue.copy(alpha = 0.08f),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Text(
                            text = "Mahasiswa", // TODO: ganti dengan roleLabel
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 4.dp),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = IelsBlue
                        )
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))

                // Card Form
                Surface(
                    modifier = Modifier
                        .widthIn(max = 400.dp)
                        .fillMaxWidth(0.95f),
                    shape = RoundedCornerShape(18.dp),
                    shadowElevation = 8.dp,
                    color = Color.White
                ) {
                    Column(
                        modifier = Modifier.padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Edit Profil",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = TextDark
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        ProfileFormField(
                            label = "Nama Lengkap",
                            value = "", // TODO: ganti dengan state name
                            onValueChange = {},
                            leadingIcon = Icons.Filled.Person
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        ProfileFormField(
                            label = "NIM / Email",
                            value = "", // TODO: ganti dengan state nim/email
                            onValueChange = {},
                            leadingIcon = Icons.Filled.Email
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        ProfileFormField(
                            label = "Password Baru (Opsional)",
                            value = "", // TODO: ganti dengan state password
                            onValueChange = {},
                            leadingIcon = Icons.Filled.Lock,
                            isPassword = true
                        )
                        Spacer(modifier = Modifier.height(28.dp))
                        Button(
                            onClick = { /* TODO: Simpan perubahan */ },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = IelsMagenta, contentColor = Color.White)
                        ) {
                            Text("Simpan Perubahan", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                        }
                    }
                }
            }
        }
    }
}

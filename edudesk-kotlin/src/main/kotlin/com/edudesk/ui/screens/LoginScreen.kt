package com.edudesk.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edudesk.services.AuthService
import com.edudesk.services.SessionManager
import com.edudesk.ui.navigation.NavController
import com.edudesk.ui.navigation.Screen
import com.edudesk.ui.theme.IelsMagenta

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen() {
    var identifier by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    val authService = remember { AuthService() }

    Box(
            modifier = Modifier.fillMaxSize().background(Color.White),
            contentAlignment = Alignment.Center
    ) {
        Column(
                modifier = Modifier.width(400.dp).padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                    painter = painterResource("IELS Logo 3.png"),
                    contentDescription = "EduDesk Logo",
                    modifier = Modifier.width(200.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text("Masuk ke akun LMS Anda", fontSize = 16.sp, color = Color(0xFF6A6F73))

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                    value = identifier,
                    onValueChange = { identifier = it },
                    label = { Text("Email atau NIM") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation(),
                    singleLine = true
            )

            if (error != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(error!!, color = Color.Red, fontSize = 14.sp)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                    onClick = {
                        val user = authService.login(identifier, password)
                        if (user != null) {
                            SessionManager.currentUser = user
                            NavController.navigateTo(Screen.Home)
                        } else {
                            error = "NIM/Email atau Password salah"
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = IelsMagenta,
                        contentColor = Color.White
                    )
            ) { Text("Login", fontWeight = FontWeight.Bold, color = Color.White) }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = {}) { Text("Lupa password?", color = IelsMagenta) }
        }
    }
}

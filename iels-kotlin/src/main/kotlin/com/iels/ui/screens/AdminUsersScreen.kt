package com.iels.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.iels.models.RegisterRequest
import com.iels.models.UpdateProfileRequest
import com.iels.models.User
import com.iels.services.UserService
import com.iels.ui.components.AdminLayout
import kotlinx.coroutines.launch
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun DialogTextField(value: String, onValueChange: (String) -> Unit, label: String, isPassword: Boolean = false) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, fontSize = 13.sp) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
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

@Composable
fun AdminUsersScreen() {
    val userService = remember { UserService() }
    var users by remember { mutableStateOf<List<User>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()

    // Dialog States
    var showAddDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf<User?>(null) }
    var showDeleteConfirm by remember { mutableStateOf<User?>(null) }

    fun refreshUsers() {
        isLoading = true
        coroutineScope.launch {
            users = userService.getUsers()
            isLoading = false
        }
    }

    LaunchedEffect(Unit) {
        refreshUsers()
    }

    AdminLayout(activeMenu = "Kelola Pengguna") {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 48.dp, vertical = 32.dp)
        ) {
            // Header Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Kelola Pengguna", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Manajemen data siswa, instruktur, dan admin sistem.", color = Color(0xFF64748B), fontSize = 14.sp)
                }

                Button(
                    onClick = { showAddDialog = true },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF496E96))
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Tambah", modifier = Modifier.size(18.dp), tint = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Tambah Pengguna", fontWeight = FontWeight.SemiBold, color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Users Table
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, Color(0xFFE2E8F0))
            ) {
                Column(modifier = Modifier.fillMaxWidth().padding(24.dp)) {
                    // Table Header
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFF8FAFC), RoundedCornerShape(8.dp))
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("NAMA / EMAIL", modifier = Modifier.weight(2f), fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF64748B))
                        Text("NIM / ID", modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF64748B))
                        Text("PERAN", modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF64748B))
                        Text("STATUS", modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF64748B))
                        Text("AKSI", modifier = Modifier.width(100.dp), fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF64748B))
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    if (isLoading) {
                        Box(modifier = Modifier.fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator(color = Color(0xFF496E96))
                        }
                    } else if (users.isEmpty()) {
                        Box(modifier = Modifier.fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) {
                            Text("Belum ada data pengguna.", color = Color(0xFF94A3B8))
                        }
                    } else {
                        LazyColumn(modifier = Modifier.weight(1f, fill = false)) {
                            items(users) { user ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp, vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(2f)) {
                                        Text(user.name, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = Color(0xFF0F172A))
                                        Text(user.email, fontSize = 12.sp, color = Color(0xFF64748B))
                                    }
                                    
                                    Text(user.nim, modifier = Modifier.weight(1f), fontSize = 13.sp, color = Color(0xFF0F172A))
                                    
                                    Box(modifier = Modifier.weight(1f)) {
                                        Surface(
                                            color = if (user.role == "admin") Color(0xFFFEE2E2) else Color(0xFFE0E7FF),
                                            shape = RoundedCornerShape(6.dp)
                                        ) {
                                            Text(
                                                text = user.role.uppercase(),
                                                color = if (user.role == "admin") Color(0xFF991B1B) else Color(0xFF3730A3),
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 10.sp,
                                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                            )
                                        }
                                    }

                                    Box(modifier = Modifier.weight(1f)) {
                                        Switch(
                                            checked = user.isActive,
                                            onCheckedChange = { active ->
                                                coroutineScope.launch {
                                                    val success = userService.updateUserStatus(user.id, active)
                                                    if (success) refreshUsers()
                                                }
                                            },
                                            colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = Color(0xFF10B981))
                                        )
                                    }

                                    Row(modifier = Modifier.width(100.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                        IconButton(onClick = { showEditDialog = user }, modifier = Modifier.size(32.dp)) {
                                            Icon(Icons.Default.Edit, contentDescription = "Edit", tint = Color(0xFF496E96), modifier = Modifier.size(18.dp))
                                        }
                                        IconButton(onClick = { showDeleteConfirm = user }, modifier = Modifier.size(32.dp)) {
                                            Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color(0xFFEF4444), modifier = Modifier.size(18.dp))
                                        }
                                    }
                                }
                                HorizontalDivider(color = Color(0xFFF1F5F9))
                            }
                        }
                    }
                }
            }
        }
    }

    // ADD USER DIALOG
    if (showAddDialog) {
        var newName by remember { mutableStateOf("") }
        var newNim by remember { mutableStateOf("") }
        var newEmail by remember { mutableStateOf("") }
        var newPass by remember { mutableStateOf("") }
        var newRole by remember { mutableStateOf("student") }
        var isSaving by remember { mutableStateOf(false) }

        AlertDialog(
            onDismissRequest = { if (!isSaving) showAddDialog = false },
            containerColor = Color.White,
            shape = RoundedCornerShape(16.dp),
            title = { Text("Tambah Pengguna Baru", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color(0xFF0F172A)) },
            text = {
                Column {
                    DialogTextField(newName, { newName = it }, "Nama Lengkap")
                    Spacer(modifier = Modifier.height(12.dp))
                    DialogTextField(newNim, { newNim = it }, "NIM / NIDN")
                    Spacer(modifier = Modifier.height(12.dp))
                    DialogTextField(newEmail, { newEmail = it }, "Email")
                    Spacer(modifier = Modifier.height(12.dp))
                    DialogTextField(newPass, { newPass = it }, "Password Sementara", isPassword = true)
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Text("Peran:", fontWeight = FontWeight.Medium, fontSize = 13.sp, color = Color(0xFF64748B))
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        listOf("student", "admin").forEach { role ->
                            val isSelected = newRole == role
                            Surface(
                                shape = RoundedCornerShape(100),
                                color = if (isSelected) Color(0xFF496E96) else Color(0xFFF1F5F9),
                                modifier = Modifier
                                    .clickable { newRole = role }
                                    .padding(end = 8.dp)
                            ) {
                                Text(
                                    text = role.capitalize(),
                                    color = if (isSelected) Color.White else Color(0xFF64748B),
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    fontSize = 13.sp,
                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        isSaving = true
                        coroutineScope.launch {
                            val success = userService.createUser(RegisterRequest(newEmail, newPass, newName, newNim, newRole))
                            if (success) {
                                showAddDialog = false
                                refreshUsers()
                            }
                            isSaving = false
                        }
                    },
                    enabled = !isSaving && newName.isNotBlank() && newNim.isNotBlank() && newPass.isNotBlank()
                ) {
                    Text(if (isSaving) "Menyimpan..." else "Simpan")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddDialog = false }, enabled = !isSaving) { Text("Batal") }
            }
        )
    }

    // EDIT USER DIALOG
    showEditDialog?.let { user ->
        var editName by remember { mutableStateOf(user.name) }
        var editNim by remember { mutableStateOf(user.nim) }
        var editEmail by remember { mutableStateOf(user.email) }
        var editPass by remember { mutableStateOf("") }
        var isSaving by remember { mutableStateOf(false) }

        AlertDialog(
            onDismissRequest = { if (!isSaving) showEditDialog = null },
            containerColor = Color.White,
            shape = RoundedCornerShape(16.dp),
            title = { Text("Edit Pengguna", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color(0xFF0F172A)) },
            text = {
                Column {
                    DialogTextField(editName, { editName = it }, "Nama Lengkap")
                    Spacer(modifier = Modifier.height(12.dp))
                    DialogTextField(editNim, { editNim = it }, "NIM / NIDN")
                    Spacer(modifier = Modifier.height(12.dp))
                    DialogTextField(editEmail, { editEmail = it }, "Email")
                    Spacer(modifier = Modifier.height(12.dp))
                    DialogTextField(editPass, { editPass = it }, "Password Baru (Kosongkan jika tidak diubah)", isPassword = true)
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        isSaving = true
                        coroutineScope.launch {
                            val success = userService.updateProfile(user.id, editName, editNim, editEmail, editPass.takeIf { it.isNotBlank() })
                            if (success) {
                                showEditDialog = null
                                refreshUsers()
                            }
                            isSaving = false
                        }
                    },
                    enabled = !isSaving && editName.isNotBlank() && editNim.isNotBlank()
                ) {
                    Text(if (isSaving) "Menyimpan..." else "Simpan Perubahan")
                }
            },
            dismissButton = {
                TextButton(onClick = { showEditDialog = null }, enabled = !isSaving) { Text("Batal") }
            }
        )
    }

    // DELETE CONFIRM DIALOG
    showDeleteConfirm?.let { user ->
        var isDeleting by remember { mutableStateOf(false) }
        AlertDialog(
            onDismissRequest = { if (!isDeleting) showDeleteConfirm = null },
            containerColor = Color.White,
            shape = RoundedCornerShape(16.dp),
            title = { Text("Hapus Pengguna", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color(0xFF0F172A)) },
            text = { Text("Apakah Anda yakin ingin menghapus akun \${user.name} (\${user.nim})? Tindakan ini tidak dapat dibatalkan.", color = Color(0xFF475569)) },
            confirmButton = {
                Button(
                    onClick = {
                        isDeleting = true
                        coroutineScope.launch {
                            val success = userService.deleteUser(user.id)
                            if (success) {
                                showDeleteConfirm = null
                                refreshUsers()
                            }
                            isDeleting = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDC2626)),
                    enabled = !isDeleting
                ) {
                    Text(if (isDeleting) "Menghapus..." else "Ya, Hapus")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteConfirm = null }, enabled = !isDeleting) { Text("Batal") }
            }
        )
    }
}

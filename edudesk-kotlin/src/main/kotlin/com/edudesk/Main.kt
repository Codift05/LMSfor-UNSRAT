package com.edudesk

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import androidx.compose.ui.unit.dp
import com.edudesk.ui.theme.EduDeskTheme
import com.edudesk.ui.screens.HomeScreen
import com.edudesk.ui.screens.MyLearningScreen
import com.edudesk.ui.screens.AssignmentsScreen
import com.edudesk.ui.screens.LoginScreen
import com.edudesk.ui.screens.InstructorDashboardScreen
import com.edudesk.ui.screens.AdminDashboardScreen
import com.edudesk.ui.navigation.NavController
import com.edudesk.ui.navigation.Screen
import com.edudesk.database.DatabaseSetup
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment

fun main() = application {
    // Database initialization might take time, in real app show splash
    DatabaseSetup.init()

    Window(
        onCloseRequest = ::exitApplication,
        title = "EduDesk LMS",
        state = rememberWindowState(width = 1280.dp, height = 800.dp)
    ) {
        EduDeskTheme {
            when (NavController.currentScreen) {
                Screen.Login -> LoginScreen()
                Screen.Home -> HomeScreen()
                Screen.InstructorDashboard -> InstructorDashboardScreen()
                Screen.AdminDashboard -> AdminDashboardScreen()
                Screen.MyLearning -> MyLearningScreen()
                Screen.Assignments -> AssignmentsScreen()
                Screen.Profile -> {
                    Box(modifier = androidx.compose.ui.Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Profile Screen coming soon...")
                    }
                }
            }
        }
    }
}

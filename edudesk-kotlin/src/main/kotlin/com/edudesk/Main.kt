package com.edudesk

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.edudesk.database.DatabaseSetup
import com.edudesk.ui.navigation.NavController
import com.edudesk.ui.navigation.Screen
import com.edudesk.ui.screens.AdminDashboardScreen
import com.edudesk.ui.screens.AssignmentsScreen
import com.edudesk.ui.screens.HomeScreen
import com.edudesk.ui.screens.InstructorDashboardScreen
import com.edudesk.ui.screens.LoginScreen
import com.edudesk.ui.screens.MyLearningScreen
import com.edudesk.ui.screens.ProfileScreen
import com.edudesk.ui.screens.CourseRegistrationScreen
import com.edudesk.ui.screens.CourseDetailScreen
import com.edudesk.ui.screens.MessagesScreen
import com.edudesk.ui.screens.AccountSettingsScreen
import com.edudesk.ui.screens.HelpScreen
import com.edudesk.ui.theme.EduDeskTheme

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
                Screen.Profile -> ProfileScreen()
                Screen.CourseRegistration -> CourseRegistrationScreen()
                Screen.CourseDetail -> CourseDetailScreen()
                Screen.Messages -> MessagesScreen()
                Screen.AccountSettings -> AccountSettingsScreen()
                Screen.Help -> HelpScreen()
            }
        }
    }
}

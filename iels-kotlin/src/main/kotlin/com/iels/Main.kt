package com.iels

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.iels.ui.navigation.NavController
import com.iels.ui.navigation.Screen
import com.iels.ui.screens.AdminDashboardScreen
import com.iels.ui.screens.AdminOverviewScreen
import com.iels.ui.screens.AdminMonitoringScreen
import com.iels.ui.screens.AdminResultsScreen
import com.iels.ui.screens.AdminSettingsScreen
import com.iels.ui.screens.AdminUsersScreen
import com.iels.ui.screens.HomeScreen
import com.iels.ui.screens.LandingScreen
import com.iels.ui.screens.LoginScreen
import com.iels.ui.screens.RegisterScreen
import com.iels.ui.screens.SecureExamScreen
import com.iels.ui.theme.IelsTheme

fun main() = application {
    val windowState = rememberWindowState(width = 1280.dp, height = 800.dp)
    
    // Dynamic kiosk mode toggle
    LaunchedEffect(NavController.currentScreen) {
        if (NavController.currentScreen == Screen.SecureExam) {
            windowState.placement = WindowPlacement.Fullscreen
        } else if (windowState.placement == WindowPlacement.Fullscreen) {
            windowState.placement = WindowPlacement.Floating
        }
    }

    Window(
            onCloseRequest = ::exitApplication,
            title = "Iels Secure CBT",
            state = windowState,
            alwaysOnTop = (NavController.currentScreen == Screen.SecureExam)
    ) {
        IelsTheme {
            when (NavController.currentScreen) {
                Screen.Login -> LoginScreen()
                Screen.Home -> HomeScreen()
                Screen.SecureExam -> SecureExamScreen()
                Screen.AdminDashboard -> AdminDashboardScreen()
                Screen.AdminOverview -> AdminOverviewScreen()
                Screen.AdminMonitoring -> AdminMonitoringScreen()
                Screen.AdminResults -> AdminResultsScreen()
                Screen.AdminSettings -> AdminSettingsScreen()
                Screen.AdminUsers -> AdminUsersScreen()
                Screen.Register -> RegisterScreen()
                Screen.Landing -> LandingScreen()
                else -> LandingScreen()
            }
        }
    }
}

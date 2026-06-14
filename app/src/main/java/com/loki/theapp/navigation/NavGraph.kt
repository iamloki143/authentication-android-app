package com.loki.theapp.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.loki.theapp.presentation.screen.admin.AdminScreen
import com.loki.theapp.presentation.screen.auth.AuthScreen
import com.loki.theapp.presentation.screen.auth.AuthViewModel
import com.loki.theapp.presentation.screen.auth.ForgotPasswordScreen
import com.loki.theapp.presentation.screen.auth.ResetPasswordScreen
import com.loki.theapp.presentation.screen.auth.VerifyEmailScreen
import com.loki.theapp.presentation.screen.dashboard.DashboardScreen
import com.loki.theapp.presentation.screen.home.HomeScreen
import com.loki.theapp.presentation.screen.profile.ProfileScreen

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController()
) {
    val authViewModel: AuthViewModel = hiltViewModel()
    val isLoggedIn by authViewModel.isLoggedIn.collectAsState()
    val startDestination = if (isLoggedIn == true) Route.HOME else Route.AUTH

    if (isLoggedIn == null){
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

        NavHost(
            navController    = navController,
            startDestination = startDestination
        ) {

            composable(Route.AUTH) {
                AuthScreen(
                    onLoginSuccess = {
                        navController.navigate(Route.HOME) {
                            popUpTo(Route.AUTH) { inclusive = true }
                        }
                    },
                    onForgotPassword = { navController.navigate(Route.FORGOT) },
                    onRegisterSuccess = { email ->
                        navController.navigate(Route.verifyEmail(email))
                    }
                )
            }

            composable(Route.FORGOT) {
                ForgotPasswordScreen(
                    viewModel = authViewModel,
                    onBack = { navController.popBackStack() },
                    onGoReset = { navController.navigate(Route.RESET) }
                )
            }

            composable(Route.RESET) {
                ResetPasswordScreen(
                    viewModel = authViewModel,
                    onBack = { navController.popBackStack() },
                    onSuccess = {
                        navController.navigate(Route.AUTH) {
                            popUpTo(Route.AUTH) { inclusive = true }
                        }
                    }
                )
            }

            composable(
                route = Route.VERIFY_EMAIL,
                arguments = listOf(navArgument("email") { type = NavType.StringType })
            ) { backStackEntry ->
                val email = backStackEntry.arguments?.getString("email") ?: ""
                VerifyEmailScreen(
                    viewModel = authViewModel,
                    email = email,
                    onBack = { navController.popBackStack() },
                    onGoToLogin = {
                        navController.navigate(Route.AUTH) {
                            popUpTo(Route.AUTH) { inclusive = true }
                        }
                    }
                )
            }

            composable(Route.HOME) {
                HomeScreen(
                    viewModel = authViewModel,
                    onNavigateProfile = { navController.navigate(Route.PROFILE) },
                    onNavigateDashboard = { navController.navigate(Route.DASHBOARD) },
                    onNavigateAdmin = { navController.navigate(Route.ADMIN) },
                    onLogout = {
                        navController.navigate(Route.AUTH) {
                            popUpTo(Route.HOME) { inclusive = true }
                        }
                    }
                )
            }

            composable(Route.PROFILE) {
                ProfileScreen(
                    viewModel = authViewModel,
                    onBack = { navController.popBackStack() },
                    onLogout = {
                        navController.navigate(Route.AUTH) {
                            popUpTo(Route.HOME) { inclusive = true }
                        }
                    },
                    onNavigateHome = { navController.navigate(Route.HOME) },
                    onNavigateDashboard = { navController.navigate(Route.DASHBOARD) }
                )
            }

            composable(Route.DASHBOARD) {
                DashboardScreen(
                    viewModel = authViewModel,
                    onBack = { navController.popBackStack() },
                    onNavigateHome = { navController.navigate(Route.HOME) },
                    onNavigateProfile = { navController.navigate(Route.PROFILE) }
                )
            }

            composable(Route.ADMIN) {
                AdminScreen(
                    viewModel = authViewModel,
                    onBack = { navController.popBackStack() }
                )
            }
        }
}

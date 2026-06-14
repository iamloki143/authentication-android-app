package com.loki.theapp.presentation.screen.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.authapp.ui.theme.*
import com.loki.theapp.data.repository.UiState
import com.loki.theapp.presentation.screen.auth.AuthViewModel
import com.loki.theapp.presentation.screen.home.AppBottomNav
import com.loki.theapp.utils.AvatarCircle
import com.loki.theapp.utils.ErrorBanner
import com.loki.theapp.utils.InfoRow
import com.loki.theapp.utils.RoleBadge
import com.loki.theapp.utils.SuccessBanner

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: AuthViewModel,
    onBack: () -> Unit,
    onLogout: () -> Unit,
    onNavigateDashboard: () -> Unit,
    onNavigateHome: () -> Unit
) {
    val username     by viewModel.username.collectAsStateWithLifecycle()
    val email        by viewModel.email.collectAsStateWithLifecycle()
    val role         by viewModel.role.collectAsStateWithLifecycle()
    val profileState by viewModel.profileState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) { viewModel.fetchProfile() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile", fontSize = 17.sp) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Outlined.ArrowBack, "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Outlined.Edit, "Edit")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        bottomBar = {
            AppBottomNav(
                currentRoute = "profile",
                onHome       = onNavigateHome,
                onDashboard  = onNavigateDashboard,
                onProfile    = {}
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(padding)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Spacer(Modifier.height(16.dp))

            AvatarCircle(
                initials = username.take(2).ifEmpty { "US" },
                size     = 72
            )
            Text(username.ifEmpty { "User" },
                fontSize = 18.sp, fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onBackground)
            Text(email.ifEmpty { "" },
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant)

            RoleBadge(role.ifEmpty { "User" })

            Spacer(Modifier.height(4.dp))

            when (profileState) {
                is UiState.Loading -> {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(14.dp),
                            strokeWidth = 2.dp,
                            color = Purple600
                        )
                        Text("Verifying token...", fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
                is UiState.Success -> {
                    SuccessBanner("Token valid — protected profile loaded")
                }
                is UiState.Error -> {
                    ErrorBanner((profileState as UiState.Error).message)
                }
                else -> {}
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape    = RoundedCornerShape(14.dp),
                colors   = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                border   = CardDefaults.outlinedCardBorder()
            ) {
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    InfoRow(
                        label = "Username",
                        value = username.ifEmpty { "—" },
                        icon  = Icons.Outlined.Person
                    )
                    InfoRow(
                        label = "Email",
                        value = email.ifEmpty { "—" },
                        icon  = Icons.Outlined.Email,
                        valueColor = Purple600
                    )
                    InfoRow(
                        label = "Email verified",
                        value = "Yes",
                        icon  = Icons.Outlined.VerifiedUser,
                        valueColor = Teal600
                    )
                    InfoRow(
                        label = "Role",
                        value = role.ifEmpty { "User" },
                        icon  = Icons.Outlined.Shield
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(Icons.Outlined.CalendarMonth, null,
                                modifier = Modifier.size(16.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text("Member since",
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Text("Jun 2025", fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurface)
                    }
                }
            }

            Spacer(Modifier.height(4.dp))

            OutlinedButton(
                onClick = {
                    viewModel.logout()
                    onLogout()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp),
                shape    = RoundedCornerShape(12.dp),
                colors   = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                ),
                border   = ButtonDefaults.outlinedButtonBorder
            ) {
                Icon(Icons.Outlined.Logout, null,
                    modifier = Modifier.size(16.dp))
                Spacer(Modifier.width(6.dp))
                Text("Log out", fontSize = 14.sp)
            }

            Spacer(Modifier.height(8.dp))
        }
    }
}

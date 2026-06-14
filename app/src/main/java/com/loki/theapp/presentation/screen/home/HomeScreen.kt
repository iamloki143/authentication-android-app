package com.loki.theapp.presentation.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.authapp.ui.theme.*
import com.loki.theapp.presentation.screen.auth.AuthViewModel
import com.loki.theapp.utils.RoleBadge
import com.loki.theapp.utils.SectionLabel
import com.loki.theapp.utils.StatCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: AuthViewModel,
    onNavigateProfile: () -> Unit,
    onNavigateDashboard: () -> Unit,
    onNavigateAdmin: () -> Unit,
    onLogout: () -> Unit
) {
    val username by viewModel.username.collectAsStateWithLifecycle()
    val role     by viewModel.role.collectAsStateWithLifecycle()
    val isAdmin  = role.lowercase() == "admin"

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Home", fontSize = 17.sp) },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Outlined.Notifications, "Notifications")
                    }
                    IconButton(onClick = {
                        viewModel.logout()
                        onLogout()
                    }) {
                        Icon(Icons.Outlined.Logout, "Logout")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        bottomBar = {
            AppBottomNav(
                currentRoute     = "home",
                onHome           = {},
                onDashboard      = onNavigateDashboard,
                onProfile        = onNavigateProfile
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(Modifier.height(8.dp))
            Column {
                Text("Welcome back,",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(username.ifEmpty { "User" },
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onBackground)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .padding(14.dp)
                    ) {
                        Text("Role", fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Spacer(Modifier.height(6.dp))
                        RoleBadge(role.ifEmpty { "User" })
                    }
                }
                StatCard(
                    label    = "Status",
                    value    = "Verified",
                    modifier = Modifier.weight(1f)
                )
            }

            SectionLabel("Quick actions")

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                QuickActionCard(
                    icon     = Icons.Outlined.Person,
                    title    = "Profile",
                    subtitle = "View your info",
                    onClick  = onNavigateProfile,
                    modifier = Modifier.weight(1f)
                )
                QuickActionCard(
                    icon     = Icons.Outlined.BarChart,
                    title    = "Dashboard",
                    subtitle = "Activity overview",
                    onClick  = onNavigateDashboard,
                    modifier = Modifier.weight(1f)
                )
            }

            if (isAdmin) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Amber50)
                        .clickable { onNavigateAdmin() }
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(Icons.Outlined.AdminPanelSettings, null,
                        tint = Amber800, modifier = Modifier.size(26.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Admin panel",
                            fontSize = 14.sp, fontWeight = FontWeight.Medium,
                            color = Amber800)
                        Text("Manage users & system",
                            fontSize = 12.sp, color = Amber600)
                    }
                    Icon(Icons.Outlined.ChevronRight, null,
                        tint = Amber600, modifier = Modifier.size(18.dp))
                }
            }

            Spacer(Modifier.height(8.dp))
        }
    }
}




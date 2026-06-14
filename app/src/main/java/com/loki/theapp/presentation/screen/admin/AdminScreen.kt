package com.loki.theapp.presentation.screen.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.authapp.ui.theme.*
import com.loki.theapp.data.repository.UiState
import com.loki.theapp.presentation.screen.auth.AuthViewModel
import com.loki.theapp.utils.ErrorBanner
import com.loki.theapp.utils.RoleBadge
import com.loki.theapp.utils.SectionLabel
import com.loki.theapp.utils.StatCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminScreen(
    viewModel: AuthViewModel,
    onBack: () -> Unit
) {
    val role        by viewModel.role.collectAsStateWithLifecycle()
    val adminState  by viewModel.adminState.collectAsStateWithLifecycle()
    val isAdmin     = role.lowercase() == "admin"

    LaunchedEffect(Unit) { viewModel.fetchAdminData() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Admin panel", fontSize = 17.sp) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Outlined.ArrowBack, "Back")
                    }
                },
                actions = {
                    if (isAdmin) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(Amber50)
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text("Admin only", fontSize = 11.sp,
                                fontWeight = FontWeight.Medium, color = Amber800)
                        }
                        Spacer(Modifier.width(12.dp))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { padding ->
        if (!isAdmin) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(padding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(MaterialTheme.colorScheme.errorContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Outlined.Lock, null,
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(32.dp))
                }
                Spacer(Modifier.height(20.dp))
                Text("Access denied",
                    fontSize = 20.sp, fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onBackground)
                Spacer(Modifier.height(8.dp))
                Text(
                    "This page requires the Admin role.\nYour current role is: $role",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp
                )
                Spacer(Modifier.height(24.dp))
                OutlinedButton(onClick = onBack, shape = RoundedCornerShape(12.dp)) {
                    Text("Go back")
                }
            }
        } else {
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

                when (adminState) {
                    is UiState.Loading -> {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(14.dp),
                                strokeWidth = 2.dp, color = Amber400)
                            Text("Verifying admin token...", fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                    is UiState.Success -> {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(Amber50)
                                .padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(Icons.Outlined.Shield, null,
                                tint = Amber800, modifier = Modifier.size(16.dp))
                            Text("Access granted — only for Admin",
                                fontSize = 12.sp, color = Amber800)
                        }
                    }
                    is UiState.Error -> {
                        ErrorBanner((adminState as UiState.Error).message)
                    }
                    else -> {}
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    StatCard("Total users",    "24", Modifier.weight(1f))
                    StatCard("Active users",  "11", Modifier.weight(1f))
                }

                SectionLabel("User management")
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape    = RoundedCornerShape(14.dp),
                    colors   = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    border   = CardDefaults.outlinedCardBorder()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier.padding(bottom = 12.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Purple50),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Outlined.Group, null,
                                    tint = Purple600, modifier = Modifier.size(18.dp))
                            }
                            Column {
                                Text("Registered accounts", fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.onSurface)
                                Text("3 users", fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                        HorizontalDivider(
                            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))

                        AdminUserRow("User_a",    "User",  Purple50,  Purple800)
                        AdminUserRow("admin_b",    "Admin", Amber50,   Amber800)
                        AdminUserRow("user_c", "User",  Teal50,    Teal600)
                    }
                }
            }
        }
    }
}

package com.loki.theapp.presentation.screen.dashboard

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.authapp.ui.theme.*
import com.loki.theapp.presentation.screen.auth.AuthViewModel
import com.loki.theapp.presentation.screen.home.AppBottomNav
import com.loki.theapp.utils.SectionLabel
import com.loki.theapp.utils.StatCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: AuthViewModel,
    onBack: () -> Unit,
    onNavigateProfile: () -> Unit,
    onNavigateHome: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dashboard", fontSize = 17.sp) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Outlined.ArrowBack, "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        bottomBar = {
            AppBottomNav(
                currentRoute = "dashboard",
                onHome       = onNavigateHome,
                onDashboard  = {},
                onProfile    = onNavigateProfile
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

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                StatCard("Sessions",   "14",         Modifier.weight(1f))
                StatCard("Last login", "Today 9:41", Modifier.weight(1f))
            }

            SectionLabel("Protected endpoint")
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
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(Purple50)
                                .padding(horizontal = 6.dp, vertical = 3.dp)
                        ) {
                            Text("GET", fontSize = 11.sp,
                                fontWeight = FontWeight.Medium, color = Purple600)
                        }
                        Text("/auth/profile",
                            fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurface)
                    }
                    Spacer(Modifier.height(10.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .padding(10.dp)
                    ) {
                        Text(
                            "{ \"message\": \"Welcome to protected profile\" }",
                            fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                            fontSize = 12.sp,
                            color = Teal600
                        )
                    }
                }
            }

            SectionLabel("Recent activity")
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape    = RoundedCornerShape(14.dp),
                colors   = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                border   = CardDefaults.outlinedCardBorder()
            ) {
                Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
                    ActivityItem("Login successful",   "09:41", Teal400)
                    ActivityItem("Token refreshed",    "09:38", Purple400)
                    ActivityItem("Email verified",     "Yesterday", Teal400)
                    ActivityItem("Account registered", "Yesterday", Purple400)
                }
            }

            Spacer(Modifier.height(8.dp))
        }
    }
}

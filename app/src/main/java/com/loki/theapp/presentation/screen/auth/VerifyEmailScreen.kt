package com.loki.theapp.presentation.screen.auth

import android.R.attr.text
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.LockReset
import androidx.compose.material.icons.outlined.MarkEmailRead
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.authapp.ui.theme.Teal50
import com.example.authapp.ui.theme.Teal600
import com.loki.theapp.data.repository.UiState
import com.loki.theapp.utils.ErrorBanner
import com.loki.theapp.utils.SuccessBanner

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerifyEmailScreen(
    viewModel: AuthViewModel,
    email:String,
    onBack: () -> Unit,
    onGoToLogin: () -> Unit
) {
    var token by remember { mutableStateOf("") }
    var tokenError by remember { mutableStateOf<String?>(null) }

    val uiState by viewModel.verifyEmailState.collectAsStateWithLifecycle()
    LaunchedEffect(uiState) {
        if (uiState is UiState.Success) {
            onGoToLogin()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Verify Email") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Outlined.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(padding)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Box(
                modifier = Modifier
                    .size(96.dp)
                    .clip(RoundedCornerShape(28.dp))
                    .background(Teal50),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Outlined.MarkEmailRead,
                    contentDescription = null,
                    tint = Teal600,
                    modifier = Modifier.size(48.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Check Your Inbox",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "We sent a verification code to your email. Enter it below to activate your account.",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(32.dp))

            AnimatedVisibility(visible = uiState is UiState.Error) {
                ErrorBanner(message = (uiState as? UiState.Error)?.message ?: "")
                Spacer(modifier = Modifier.height(12.dp))
            }
            AnimatedVisibility(visible = uiState is UiState.Success) {
                SuccessBanner(message = "Email verified! You can now log in.")
                Spacer(modifier = Modifier.height(12.dp))
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    OutlinedTextField(
                        value = token,
                        onValueChange = {
                            token = it
                            tokenError = null
                        },
                        label = { Text("Verification Code") },
                        placeholder = { Text("Paste or type your code") },
                        leadingIcon = {
                            Icon(Icons.Outlined.LockReset, contentDescription = null)
                        },
                        isError = tokenError != null,
                        supportingText = tokenError?.let { { Text(it) } },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Button(
                        onClick = {
                            if (token.isBlank()) {
                                tokenError = "Please enter the verification code"
                            } else {
                                viewModel.verifyEmail(token.trim())
                            }
                        },
                        enabled = uiState !is UiState.Loading,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp)
                    ) {
                        if (uiState is UiState.Loading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                strokeWidth = 2.dp,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        } else {
                            Icon(Icons.Outlined.MarkEmailRead, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Verify Email")
                        }
                    }

                    Divider()

                    OutlinedButton(
                        onClick = { viewModel.resendVerificationEmail(email) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp)
                    ) {
                        Icon(Icons.Outlined.Refresh, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Resend Code")
                    }

                    OutlinedButton(
                        onClick = onGoToLogin,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp)
                    ) {
                        Text("Back to Login")
                    }
                }
            }
        }
    }
}
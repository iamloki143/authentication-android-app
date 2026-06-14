package com.loki.theapp.presentation.screen.auth

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Key
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.LockReset
import androidx.compose.material.icons.outlined.Password
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.loki.theapp.data.repository.UiState
import com.loki.theapp.utils.ErrorBanner
import com.loki.theapp.utils.SuccessBanner

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResetPasswordScreen(
    viewModel: AuthViewModel,
    onBack: () -> Unit,
    onSuccess: () -> Unit
)  {

    val state by viewModel.resetPasswordState.collectAsStateWithLifecycle()

    var resetToken by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var passwordMismatch by remember { mutableStateOf(false) }

    LaunchedEffect(state) {
        if (state is UiState.Success) {
            viewModel.resetPasswordState()
            onSuccess()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Reset Password") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.Outlined.ArrowBack,
                            contentDescription = null
                        )
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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .size(90.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Outlined.LockReset,
                    contentDescription = null,
                    modifier = Modifier.size(42.dp)
                )
            }

            Spacer(Modifier.height(20.dp))

            Text(
                text = "Create New Password",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = "Enter the reset token sent to your email and choose a new password.",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(Modifier.height(28.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp)
            ) {

                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    AnimatedVisibility(
                        visible = state is UiState.Error
                    ) {
                        ErrorBanner(
                            (state as? UiState.Error)?.message ?: ""
                        )
                    }

                    AnimatedVisibility(
                        visible = passwordMismatch
                    ) {
                        ErrorBanner(
                            "Passwords do not match"
                        )
                    }

                    AnimatedVisibility(
                        visible = state is UiState.Success
                    ) {
                        SuccessBanner(
                            "Password reset successful"
                        )
                    }

                    OutlinedTextField(
                        value = resetToken,
                        onValueChange = {
                            resetToken = it
                        },
                        modifier = Modifier.fillMaxWidth(),
                        label = {
                            Text("Reset Token")
                        },
                        placeholder = {
                            Text("Paste token from email")
                        },
                        leadingIcon = {
                            Icon(
                                Icons.Outlined.Key,
                                contentDescription = null
                            )
                        },
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = newPassword,
                        onValueChange = {
                            newPassword = it
                            passwordMismatch = false
                        },
                        modifier = Modifier.fillMaxWidth(),
                        label = {
                            Text("New Password")
                        },
                        leadingIcon = {
                            Icon(
                                Icons.Outlined.Lock,
                                contentDescription = null
                            )
                        },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password
                        ),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = {
                            confirmPassword = it
                            passwordMismatch = false
                        },
                        modifier = Modifier.fillMaxWidth(),
                        label = {
                            Text("Confirm Password")
                        },
                        leadingIcon = {
                            Icon(
                                Icons.Outlined.Password,
                                contentDescription = null
                            )
                        },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password
                        ),
                        singleLine = true,
                        isError = passwordMismatch
                    )

                    Spacer(Modifier.height(4.dp))

                    Button(
                        onClick = {

                            if (newPassword != confirmPassword) {
                                passwordMismatch = true
                            } else {
                                viewModel.resetPassword(
                                    resetToken,
                                    newPassword
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp),
                        enabled = state !is UiState.Loading
                    ) {

                        if (state is UiState.Loading) {

                            CircularProgressIndicator(
                                modifier = Modifier.size(18.dp),
                                strokeWidth = 2.dp
                            )

                        } else {

                            Text(
                                text = "Reset Password",
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}
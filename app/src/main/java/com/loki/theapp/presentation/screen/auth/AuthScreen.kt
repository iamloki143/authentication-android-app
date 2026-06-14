package com.loki.theapp.presentation.screen.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.authapp.ui.theme.Purple50
import com.example.authapp.ui.theme.Purple600
import com.example.authapp.ui.theme.Purple800
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.Icon
import androidx.compose.foundation.layout.Row
import com.loki.theapp.data.repository.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(
    vm: AuthViewModel = hiltViewModel(),
    onLoginSuccess: () -> Unit,
    onForgotPassword: () -> Unit,
    onRegisterSuccess: (email: String) -> Unit
) {

    val loginState by vm.loginState.collectAsStateWithLifecycle()
    val registerState by vm.registerState.collectAsStateWithLifecycle()

    var selectedTab by remember { mutableStateOf(0) }

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    var role by remember { mutableStateOf("User") }
    var expanded by remember { mutableStateOf(false) }
    LaunchedEffect(loginState) {
        if (loginState is UiState.Success) {
            onLoginSuccess()
        }
    }
    LaunchedEffect(registerState) {
        if (registerState is UiState.Success) {
            onRegisterSuccess(email)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier= Modifier.height(120.dp))

            Text(
                text = "Welcome Back",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Purple800
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = if (selectedTab == 0)
                    "Sign in to continue"
                else
                    "Create your new account",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(32.dp))

            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = Purple50
            ) {

                listOf("Login", "Register")
                    .forEachIndexed { index, title ->

                        Tab(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            text = {
                                Text(
                                    text = title,
                                    fontWeight =
                                        if (selectedTab == index)
                                            FontWeight.Bold
                                        else
                                            FontWeight.Normal
                                )
                            }
                        )
                    }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                tonalElevation = 4.dp,
                shadowElevation = 8.dp
            ) {

                Column(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    OutlinedTextField(
                        value = username,
                        onValueChange = {
                            username = it
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        label = {
                            Text("Username")
                        },
                        placeholder = {
                            Text("Enter username")
                        }
                    )

                    OutlinedTextField(
                        value = password,
                        onValueChange = {
                            password = it
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        label = {
                            Text("Password")
                        },
                        placeholder = {
                            Text("Enter password")
                        }
                    )

                    if (selectedTab == 1) {

                        OutlinedTextField(
                            value = email,
                            onValueChange = {
                                email = it
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            label = {
                                Text("Email")
                            },
                            placeholder = {
                                Text("Enter email")
                            }
                        )

                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = {
                                expanded = !expanded
                            }
                        ) {

                            OutlinedTextField(
                                value = role,
                                onValueChange = {},
                                readOnly = true,
                                modifier = Modifier
                                    .menuAnchor()
                                    .fillMaxWidth(),
                                shape = RoundedCornerShape(16.dp),
                                label = {
                                    Text("Role")
                                },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(
                                        expanded = expanded
                                    )
                                }
                            )

                            ExposedDropdownMenu(
                                expanded = expanded,
                                onDismissRequest = {
                                    expanded = false
                                }
                            ) {

                                listOf("User", "Admin")
                                    .forEach { option ->

                                        DropdownMenuItem(
                                            text = {
                                                Text(option)
                                            },
                                            onClick = {
                                                role = option
                                                expanded = false
                                            }
                                        )
                                    }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {

                            if (selectedTab == 0) {
                                vm.login(
                                    username,
                                    password
                                )
                            } else {
                                vm.register(
                                    username,
                                    email,
                                    password,
                                    role
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        val isLoading = (selectedTab == 0 && loginState is UiState.Loading) ||
                                (selectedTab == 1 && registerState is UiState.Loading)
                        if (isLoading){
                            CircularProgressIndicator(
                                modifier = Modifier.size(22.dp),
                                strokeWidth = 2.5.dp,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }else {
                            Text(
                                text =
                                    if (selectedTab == 0)
                                        "Sign In"
                                    else
                                        "Create Account",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }
                    when (val state = registerState) {
                        is UiState.Error -> {
                            Text(
                                text = state.message,
                                color = Color.Red
                            )
                        }
                        else -> {}
                    }
                    when (val state = loginState) {
                        is UiState.Error -> {
                            Text(
                                text = state.message,
                                color = Color.Red
                            )
                        }
                        else -> {}
                    }

                    if (selectedTab == 0) {

                        Text(
                            text = "Forgot Password?",
                            color = Purple600,
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .clickable{onForgotPassword()}
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    Color(0xFFFFF3CD)
                                )
                                .padding(12.dp)
                        ){
                            Row() {
                                Icon(
                                    imageVector = Icons.Outlined.Email,
                                    contentDescription = "Email",
                                    tint = Purple600
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "A verification email will be sent before login.",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }

                        }

                    }
                }
            }
        }
    }
}
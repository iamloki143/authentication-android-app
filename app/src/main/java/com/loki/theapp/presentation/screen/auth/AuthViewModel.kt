package com.loki.theapp.presentation.screen.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.loki.theapp.data.remote.dto.emailverify.EmailVerificationDto
import com.loki.theapp.data.remote.dto.loginDto.LoginRequest
import com.loki.theapp.data.remote.dto.loginDto.LoginResponse
import com.loki.theapp.data.remote.dto.registerDto.RegisterRequest
import com.loki.theapp.data.remote.dto.registerDto.RegisterResponse
import com.loki.theapp.data.repository.AuthRepository
import com.loki.theapp.data.repository.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repo: AuthRepository
): ViewModel() {
    val isLoggedIn : StateFlow<Boolean?> = repo.isLoggedIn.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )
    private val _loginState = MutableStateFlow<UiState<LoginResponse>>(UiState.Idle)
    val loginState = _loginState.asStateFlow()

    private val _registerState = MutableStateFlow<UiState<RegisterResponse>>(UiState.Idle)
    val registerState = _registerState.asStateFlow()
    private val _forgotPasswordState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val forgotPasswordState = _forgotPasswordState.asStateFlow()

    private val _resetPasswordState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val resetPasswordState = _resetPasswordState.asStateFlow()

    init {
        viewModelScope.launch {
            repo.getUserInfo().collect { (username, email, role) ->
                _username.value = username
                _email.value = email
                _role.value = role
            }
        }
    }

    fun resetPasswordState() {
        _resetPasswordState.value = UiState.Idle
    }

    private val _username = MutableStateFlow("")
    val username = _username.asStateFlow()

    private val _role = MutableStateFlow("User")
    val role = _role.asStateFlow()

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _profileState = MutableStateFlow<UiState<String>>(UiState.Idle)

    val profileState = _profileState.asStateFlow()

    private val _adminState = MutableStateFlow<UiState<String>>(UiState.Idle)

    val adminState = _adminState.asStateFlow()


    fun login(username: String, password: String) {
        viewModelScope.launch {
            _loginState.value = UiState.Loading
            val result = repo.login(LoginRequest(username, password))
            _loginState.value = result
            if (result is UiState.Success) {
                _username.value = username
                _role.value = result.data.role
                _email.value = result.data.email
            }
        }
    }
    fun register(username: String,email: String,password: String,role: String){
        viewModelScope.launch {
            _registerState.value = UiState.Loading
            _registerState.value= repo.register(RegisterRequest(username,email,password,role))
        }
    }
    fun logout(){
        viewModelScope.launch {
            repo.logout()
            _username.value = ""
            _email.value = ""
            _role.value = "User"
            _loginState.value = UiState.Idle
        }
    }
    fun forgotPassword(email: String) {
        viewModelScope.launch {
            _forgotPasswordState.value = UiState.Loading
            _forgotPasswordState.value = repo.forgotPassword(email)
        }
    }

    fun resetPassword(token: String, newPassword: String) {
        viewModelScope.launch {
            _resetPasswordState.value = UiState.Loading
            _resetPasswordState.value = repo.resetPassword(token, newPassword)
        }
    }
    fun fetchProfile() {
        viewModelScope.launch {
            _profileState.value = UiState.Loading

            try {
                _profileState.value =
                    UiState.Success("Profile Loaded")
            } catch (e: Exception) {
                _profileState.value =
                    UiState.Error("Failed")
            }
        }
    }
    fun fetchAdminData() {
        viewModelScope.launch {
            _adminState.value = UiState.Loading

            try {
                _adminState.value =
                    UiState.Success("Admin Access")
            } catch (e: Exception) {
                _adminState.value =
                    UiState.Error("Access Denied")
            }
        }
    }
    private val _verifyEmailState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val verifyEmailState = _verifyEmailState.asStateFlow()

    fun verifyEmail(token: String) {
        viewModelScope.launch {
            _verifyEmailState.value = UiState.Loading
            _verifyEmailState.value = repo.verifyEmail(token)
        }
    }
    private val _resendVerificationState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val resendVerificationState = _resendVerificationState.asStateFlow()

    fun resendVerificationEmail(email: String) {
        viewModelScope.launch {
            _resendVerificationState.value = UiState.Loading
            _resendVerificationState.value = repo.resendVerificationEmail(email)
        }
    }
}
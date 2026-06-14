package com.loki.theapp.data.repository

import retrofit2.HttpException
import com.loki.theapp.data.local.datastore.TokenManagaer
import com.loki.theapp.data.remote.api.AuthApis.ApiService
import com.loki.theapp.data.remote.dto.emailverify.EmailVerificationDto
import com.loki.theapp.data.remote.dto.forgotresetPasswordDto.ForgotPasswordRequest
import com.loki.theapp.data.remote.dto.forgotresetPasswordDto.ResetPasswordRequest
import com.loki.theapp.data.remote.dto.loginDto.LoginRequest
import com.loki.theapp.data.remote.dto.loginDto.LoginResponse
import com.loki.theapp.data.remote.dto.refreshDto.RefreshTokenRequest
import com.loki.theapp.data.remote.dto.registerDto.RegisterRequest
import com.loki.theapp.data.remote.dto.registerDto.RegisterResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import okio.IOException
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val api: ApiService,
    private val tokenManager: TokenManagaer
) {
    val isLoggedIn: Flow<Boolean> = tokenManager.accessTokenFlow.map { it !=null }

    suspend fun login(
        req: LoginRequest
    ): UiState<LoginResponse>{
        return try {
            val response = api.login(req)
            tokenManager.saveToken(response.accessToken,response.refreshToken)
            tokenManager.saveUserInfo(response.username,response.email,response.role)
            UiState.Success(response)
        }catch (e: HttpException){
            UiState.Error(when(e.code()){
                401 -> "Invalid Username or password"
                else -> "Error: ${e.code()}"
            })
        }catch (e: IOException){
            UiState.Error("No Internet Connection")
        }
    }

    suspend fun register(
        req: RegisterRequest
    ): UiState<RegisterResponse>{
        return try {
            UiState.Success(api.register(req))
        }catch (e: HttpException){
            UiState.Error("Registeration failed : ${e.code()}")
        }catch (e: IOException){
            UiState.Error("No internet connection")
        }
    }

    suspend fun logout() {
        val token = tokenManager.getRefreshToken() ?: run {
            tokenManager.clearToken()
            return
        }
        try {
            api.logout(RefreshTokenRequest(token))
        } catch (_: Exception) { }
        finally {
            tokenManager.clearToken()
        }
    }
    suspend fun forgotPassword(
        email: String
    ): UiState<Unit> {
        return try {
            api.forgotPassword(
                ForgotPasswordRequest(email)
            )
            UiState.Success(Unit)
        } catch (e: Exception) {
            UiState.Error(e.message ?: "Unknown Error")
        }
    }
    suspend fun resetPassword(
        token: String,
        password: String
    ): UiState<Unit> {
        return try {
            api.resetPassword(
                ResetPasswordRequest(
                    token,
                    password
                )
            )
            UiState.Success(Unit)
        } catch (e: Exception) {
            UiState.Error(e.message ?: "Unknown Error")
        }
    }
    suspend fun verifyEmail(token: String): UiState<Unit> {
        return try {
            val response = api.verifyEmail(EmailVerificationDto(token))
            if (response.isSuccessful) {
                UiState.Success(Unit)
            } else {
                UiState.Error("Invalid or expired code")
            }
        } catch (e: Exception) {
            UiState.Error("Something went wrong")
        }
    }
    suspend fun resendVerificationEmail(email: String): UiState<Unit> {
        return try {
            val response = api.resendVerificationEmail(ForgotPasswordRequest(email))
            if (response.isSuccessful) {
                UiState.Success(Unit)
            } else {
                UiState.Error("Failed to resend email")
            }
        } catch (e: Exception) {
            UiState.Error(e.message ?: "Something went wrong")
        }
    }
    fun getUserInfo() = combine(
        tokenManager.usernameFlow,
        tokenManager.emailFlow,
        tokenManager.roleFlow
    ) { username, email, role ->
        Triple(username, email, role)
    }
}
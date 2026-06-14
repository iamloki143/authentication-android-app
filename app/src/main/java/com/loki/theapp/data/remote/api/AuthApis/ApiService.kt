package com.loki.theapp.data.remote.api.AuthApis

import com.loki.theapp.data.remote.dto.commonMessageDto.MessageResponse
import com.loki.theapp.data.remote.dto.emailverify.EmailVerificationDto
import com.loki.theapp.data.remote.dto.forgotresetPasswordDto.ForgotPasswordRequest
import com.loki.theapp.data.remote.dto.forgotresetPasswordDto.ForgotPasswordResponse
import com.loki.theapp.data.remote.dto.forgotresetPasswordDto.ResetPasswordRequest
import com.loki.theapp.data.remote.dto.loginDto.LoginRequest
import com.loki.theapp.data.remote.dto.loginDto.LoginResponse
import com.loki.theapp.data.remote.dto.refreshDto.RefreshTokenRequest
import com.loki.theapp.data.remote.dto.refreshDto.RefreshTokenResponse
import com.loki.theapp.data.remote.dto.registerDto.RegisterRequest
import com.loki.theapp.data.remote.dto.registerDto.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @POST("auth/register")
    suspend fun register(
        @Body request: RegisterRequest
    ): RegisterResponse

    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse

    @GET("auth/verify-email")
    suspend fun verifyEmail(
        @Query("token") token: String
    ): MessageResponse

    @POST("auth/refresh")
    suspend fun refresh(
        @Body request: RefreshTokenRequest
    ): RefreshTokenResponse

    @POST("auth/logout")
    suspend fun logout(
        @Body request: RefreshTokenRequest
    ): MessageResponse

    @POST("auth/forgot-password")
    suspend fun forgotPassword(
        @Body request: ForgotPasswordRequest
    ): ForgotPasswordResponse

    @POST("auth/reset-password")
    suspend fun resetPassword(
        @Body request: ResetPasswordRequest
    ): MessageResponse

    @GET("auth/profile")
    suspend fun profile(
        @Header("Authorization") bearer:String
    ): MessageResponse

    @GET("auth/admin")
    suspend fun admin(
        @Header("Authorization") bearer: String
    ): MessageResponse

    @POST("auth/verify-email")
    suspend fun verifyEmail(@Body body: EmailVerificationDto): Response<Unit>

    @POST("auth/resend-verification")
    suspend fun resendVerificationEmail(@Body body: ForgotPasswordRequest): Response<Unit>
}
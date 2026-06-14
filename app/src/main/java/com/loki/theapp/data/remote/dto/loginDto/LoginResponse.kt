package com.loki.theapp.data.remote.dto.loginDto

data class LoginResponse (
    val accessToken : String,
    val refreshToken : String,
    val role: String,
    val email: String,
    val username: String
)
package com.loki.theapp.data.remote.dto.registerDto

data class RegisterResponse (
    val message : String,
    val emailVerifyToken : String
)
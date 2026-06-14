package com.loki.theapp.data.remote.dto.registerDto

data class RegisterRequest (
    val username: String,
    val email:String,
    val password : String,
    val role: String
)
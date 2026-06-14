package com.loki.theapp.data.remote.dto.forgotresetPasswordDto

data class ResetPasswordRequest(
    val resetToken: String,
    val newPassword: String
)
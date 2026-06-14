package com.loki.theapp.navigation

object Route {
    const val AUTH           = "auth"
    const val FORGOT         = "forgot_password"
    const val RESET          = "reset_password"
    const val VERIFY_EMAIL = "verify_email/{email}"
    fun verifyEmail(email: String) = "verify_email/$email"
    const val HOME           = "home"
    const val PROFILE        = "profile"
    const val DASHBOARD      = "dashboard"
    const val ADMIN          = "admin"
}

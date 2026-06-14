package com.loki.theapp.data.local.datastore

import android.content.Context
import androidx.compose.ui.input.key.Key
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TokenManagaer @Inject constructor(@ApplicationContext private val context: Context){
    private object Keys{
        val ACCESS_TOKEN = stringPreferencesKey("access_token")
        val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
    }

    suspend fun saveToken(access: String,refresh: String){
        context.dataStore.edit { prefs ->
            prefs[Keys.ACCESS_TOKEN]  = access
            prefs[Keys.REFRESH_TOKEN] = refresh
        }
    }
    suspend fun clearToken() {
        context.dataStore.edit { prefs ->
            prefs.remove(Keys.ACCESS_TOKEN)
            prefs.remove(Keys.REFRESH_TOKEN)
            prefs.remove(stringPreferencesKey("username"))
            prefs.remove(stringPreferencesKey("email"))
            prefs.remove(stringPreferencesKey("role"))
        }
    }
    suspend fun saveUserInfo(username: String, email: String, role: String) {
        context.dataStore.edit { prefs ->
            prefs[stringPreferencesKey("username")] = username
            prefs[stringPreferencesKey("email")] = email
            prefs[stringPreferencesKey("role")] = role
        }
    }
    val usernameFlow: Flow<String> = context.dataStore.data.map { it[stringPreferencesKey("username")] ?: "" }
    val emailFlow: Flow<String> = context.dataStore.data.map { it[stringPreferencesKey("email")] ?: "" }
    val roleFlow: Flow<String> = context.dataStore.data.map { it[stringPreferencesKey("role")] ?: "" }
    val accessTokenFlow: Flow<String?> = context.dataStore.data.map{prefs -> prefs[Keys.ACCESS_TOKEN]}
    val refreshTokenFlow:Flow<String?> = context.dataStore.data.map{prefs -> prefs[Keys.REFRESH_TOKEN]}

    suspend fun getAccessToken(): String? =context.dataStore.data.map{it[Keys.ACCESS_TOKEN]}.first()
    suspend fun getRefreshToken():String? = context.dataStore.data.map{it[Keys.REFRESH_TOKEN]}.first()
}
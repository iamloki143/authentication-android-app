package com.loki.theapp.di

import com.loki.theapp.data.local.datastore.TokenManagaer
import com.loki.theapp.data.remote.api.AuthApis.ApiService
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModel {

    @Provides @Singleton
    fun provideOkHttpClient(tokenManager: TokenManagaer): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val token = runBlocking { tokenManager.getAccessToken() }
                val request = if (token != null) {
                    chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer $token")
                        .build()
                } else chain.request()
                chain.proceed(request)
            }
            .authenticator { _, response ->
                val refreshToken = runBlocking { tokenManager.getRefreshToken() }
                    ?: return@authenticator null

                return@authenticator try {
                    val refreshResponse = runBlocking {
                        response.request.url.toString().let {
                            okhttp3.OkHttpClient().newCall(
                                okhttp3.Request.Builder()
                                    .url("http://10.127.197.208:5062/api/auth/refresh")
                                    .post(
                                        okhttp3.RequestBody.create(
                                            "application/json".toMediaTypeOrNull(),
                                            """{"refreshToken":"$refreshToken"}"""
                                        )
                                    )
                                    .build()
                            ).execute()
                        }
                    }

                    if (refreshResponse.isSuccessful) {
                        val newToken = refreshResponse.body?.string()
                            ?.let { org.json.JSONObject(it).getString("accessToken") }
                            ?: return@authenticator null

                        runBlocking { tokenManager.saveToken(newToken, refreshToken) }

                        response.request.newBuilder()
                            .header("Authorization", "Bearer $newToken")
                            .build()
                    } else {
                        runBlocking { tokenManager.clearToken() }
                        null
                    }
                } catch (e: Exception) {
                    runBlocking { tokenManager.clearToken() }
                    null
                }
            }
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }
    @Provides @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl("http://192.168.1.15:5062/api/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)
}
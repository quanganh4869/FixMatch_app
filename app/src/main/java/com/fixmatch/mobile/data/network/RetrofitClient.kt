package com.fixmatch.mobile.data.network

import com.fixmatch.mobile.data.network.interceptor.AuthInterceptor
import com.fixmatch.mobile.data.settings.TokenManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    
    private fun createOkHttpClient(tokenManager: TokenManager): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(tokenManager))
            .addInterceptor(loggingInterceptor)
            .connectTimeout(NetworkConfig.CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(NetworkConfig.READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(NetworkConfig.WRITE_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    fun createRetrofit(tokenManager: TokenManager): Retrofit {
        return Retrofit.Builder()
            .baseUrl(NetworkConfig.BASE_URL)
            .client(createOkHttpClient(tokenManager))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

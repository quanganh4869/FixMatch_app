package com.fixmatch.mobile.data.network.api

import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Headers

interface AuthApi {
    @Headers("No-Authentication: true")
    @POST("auth/login")
    suspend fun login(@Body request: Any): Any

    @Headers("No-Authentication: true")
    @POST("auth/register")
    suspend fun register(@Body request: Any): Any
}

interface UserApi {
    @retrofit2.http.GET("users/me")
    suspend fun getProfile(): Any
}

interface WorkerApi {
    @retrofit2.http.GET("workers")
    suspend fun getWorkers(
        @retrofit2.http.Query("category") category: String?,
        @retrofit2.http.Query("page") page: Int,
        @retrofit2.http.Query("page_size") pageSize: Int
    ): Any

    @retrofit2.http.GET("workers/{id}")
    suspend fun getWorkerDetails(@retrofit2.http.Path("id") workerId: String): Any
}

interface JobApi {
    @retrofit2.http.GET("jobs")
    suspend fun getJobs(): Any
    
    @retrofit2.http.POST("jobs")
    suspend fun requestService(@Body request: Any): Any
}

interface MessageApi {
    @retrofit2.http.GET("messages/conversations")
    suspend fun getConversations(): Any
}

// These are mocked out with `Any` return types for now since DTOs are in Phase 3.

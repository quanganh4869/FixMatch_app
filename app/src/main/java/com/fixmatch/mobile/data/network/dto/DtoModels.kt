package com.fixmatch.mobile.data.network.dto

import com.google.gson.annotations.SerializedName
import com.fixmatch.mobile.domain.model.*

data class UserDto(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("role") val role: String,
    @SerializedName("profile_image_url") val profileImageUrl: String?,
    @SerializedName("is_premium") val isPremium: Boolean
) {
    fun toDomain(): User = User(
        id = id,
        name = name,
        email = email,
        role = Role.valueOf(role.uppercase()),
        profileImageUrl = profileImageUrl,
        isPremium = isPremium
    )
}

data class WorkerDto(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("title") val title: String,
    @SerializedName("category") val category: String,
    @SerializedName("rating") val rating: Double,
    @SerializedName("jobs_completed") val jobsCompleted: Int,
    @SerializedName("hourly_rate") val hourlyRate: Double,
    @SerializedName("profile_image_url") val profileImageUrl: String,
    @SerializedName("is_pro") val isPro: Boolean,
    @SerializedName("location") val location: String
) {
    fun toDomain(): Worker = Worker(
        id = id,
        name = name,
        title = title,
        category = category,
        rating = rating,
        jobsCompleted = jobsCompleted,
        hourlyRate = hourlyRate,
        profileImageUrl = profileImageUrl,
        isPro = isPro,
        location = location
    )
}

data class JobDto(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("status") val status: String,
    @SerializedName("scheduled_time") val scheduledTime: String,
    @SerializedName("worker_id") val workerId: String?,
    @SerializedName("client_id") val clientId: String,
    @SerializedName("location") val location: String,
    @SerializedName("estimated_price") val estimatedPrice: Double?
) {
    fun toDomain(): Job = Job(
        id = id,
        title = title,
        description = description,
        status = JobStatus.valueOf(status.uppercase()),
        scheduledTime = scheduledTime,
        workerId = workerId,
        clientId = clientId,
        location = location,
        estimatedPrice = estimatedPrice
    )
}

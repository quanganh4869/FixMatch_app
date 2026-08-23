package com.fixmatch.mobile.domain.model

enum class Role {
    USER, WORKER, ADMIN
}

data class User(
    val id: String,
    val name: String,
    val email: String,
    val role: Role,
    val profileImageUrl: String?,
    val isPremium: Boolean
)

data class Worker(
    val id: String,
    val name: String,
    val title: String,
    val category: String,
    val rating: Double,
    val jobsCompleted: Int,
    val hourlyRate: Double,
    val profileImageUrl: String,
    val isPro: Boolean,
    val location: String
)

enum class JobStatus {
    PENDING,
    SEARCHING,
    ACCEPTED,
    ON_THE_WAY,
    ARRIVED,
    IN_PROGRESS,
    PAYMENT_PENDING,
    COMPLETED,
    REVIEWED,
    CANCELLED
}

data class Job(
    val id: String,
    val title: String,
    val description: String,
    val status: JobStatus,
    val scheduledTime: String,
    val workerId: String?,
    val clientId: String,
    val location: String,
    val estimatedPrice: Double?
)

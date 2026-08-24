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
    CREATED,
    SEARCHING_WORKER,
    WORKER_FOUND,
    WORKER_ACCEPTED,
    WORKER_ON_THE_WAY,
    WORKER_ARRIVED,
    INSPECTION,
    REPAIRING,
    ADDITIONAL_COST_PENDING,
    ADDITIONAL_COST_APPROVED,
    JOB_COMPLETED,
    PAYMENT_PENDING,
    COMPLETED,
    REVIEWED,
    CANCELLED_BY_WORKER,
    CANCELLED_BY_CUSTOMER,
    NO_WORKER_FOUND
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
    val basePrice: Double = 0.0,
    val additionalCost: Double = 0.0,
    val finalPrice: Double = 0.0,
    val additionalCostReason: String? = null
)

enum class WorkerApplicationStatus {
    NONE, // No application
    DRAFT,
    SUBMITTED,
    UNDER_REVIEW,
    APPROVED,
    REJECTED,
    SUSPENDED
}

data class WorkerApplication(
    val id: String,
    val userId: String,
    val status: WorkerApplicationStatus,
    val submittedAt: String? = null,
    val reviewedAt: String? = null,
    val rejectionReason: String? = null,
    val workerCategory: String = "",
    val experienceYears: Int = 0,
    val serviceArea: String = "",
    val verificationDocuments: List<String> = emptyList()
)

data class AccountState(
    val user: User,
    val application: WorkerApplication?,
    val currentMode: AppMode
)

enum class AppMode {
    CUSTOMER, WORKER
}

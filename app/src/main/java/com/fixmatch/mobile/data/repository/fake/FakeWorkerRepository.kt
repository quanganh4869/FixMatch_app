package com.fixmatch.mobile.data.repository.fake

import com.fixmatch.mobile.domain.model.Worker
import com.fixmatch.mobile.domain.repository.WorkerRepository
import com.fixmatch.mobile.domain.util.NetworkResult
import kotlinx.coroutines.delay

class FakeWorkerRepository : WorkerRepository {
    
    private val mockWorkers = listOf(
        Worker(
            id = "w1",
            name = "John Doe",
            title = "Master Electrician",
            category = "Electrical",
            rating = 4.9,
            jobsCompleted = 124,
            hourlyRate = 50.0,
            profileImageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuBVPAXG6RnY4BLjAyEDfI8bYvMSo8ErZ_DRtWTpJy1A6g90_KUt3p3RDlUccrKKgL4c-Hv_clOOydHnpUa1RTzDz-fXaQDUn89gxwQz7R6P7wPGrpOTzksYQMzS6KUI16M7UZYECqwQRIlmcDN3vUGzjTNMYFcR2nyr5vLpREFA3tkhoWxkOQUBeP_diR8hEcCK-r9s1xoUCGyy_MxKBoYVsoXNeICTNXF0wah518M52oehjrVq8bmw",
            isPro = true,
            location = "Dist 1, HCMC"
        ),
        Worker(
            id = "w2",
            name = "Sarah Smith",
            title = "HVAC Specialist",
            category = "AC Repair",
            rating = 4.8,
            jobsCompleted = 89,
            hourlyRate = 45.0,
            profileImageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuBmAynT1MjhJeW6MBB0ltPrPCTcH5OKFDxCWOXKBM4wnroTqrawOL7Rbp-EyF9ab--Ph0jvBFrIrptHdHGLlLsUXGLOSrSdNvQloBuu2dKOGr-9OGOnTF91Ae8vae9r4HAY3xA3n43Tc3cyqnM_QLbjpyzEqDVZktTIQ-8diUsBAgiEjjmX_dU81Z-1eiqHBNeQZo-0xpxXwCaTbDoW3MFU31_E-2kIuHGa4Jo3GQeTXP6lnavScu8S",
            isPro = false,
            location = "Dist 3, HCMC"
        )
    )

    override suspend fun getWorkers(category: String?, page: Int, pageSize: Int): NetworkResult<List<Worker>> {
        delay(1000) // simulate network
        val filtered = if (category != null) mockWorkers.filter { it.category == category } else mockWorkers
        return NetworkResult.Success(filtered)
    }

    override suspend fun getWorkerDetails(workerId: String): NetworkResult<Worker> {
        delay(500)
        val worker = mockWorkers.find { it.id == workerId }
        return if (worker != null) {
            NetworkResult.Success(worker)
        } else {
            NetworkResult.Error(com.fixmatch.mobile.domain.util.DataError.Api.NOT_FOUND, "Worker not found")
        }
    }
}

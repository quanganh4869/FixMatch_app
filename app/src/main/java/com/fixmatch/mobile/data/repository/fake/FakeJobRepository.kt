package com.fixmatch.mobile.data.repository.fake

import com.fixmatch.mobile.domain.model.Job
import com.fixmatch.mobile.domain.model.JobStatus
import com.fixmatch.mobile.domain.repository.JobRepository
import com.fixmatch.mobile.domain.util.NetworkResult
import kotlinx.coroutines.delay

class FakeJobRepository : JobRepository {

    private val mockJobs = mutableListOf(
        Job(
            id = "j1",
            title = "Leaky Pipe Repair",
            description = "Water leaking from kitchen sink pipe",
            status = JobStatus.MATCHING,
            scheduledTime = "Today, 14:30",
            workerId = null,
            clientId = "c1",
            location = "Dist 1, HCMC",
            estimatedPrice = null
        ),
        Job(
            id = "j2",
            title = "AC Cleaning",
            description = "Routine maintenance for 2 units",
            status = JobStatus.IN_PROGRESS,
            scheduledTime = "Today, 16:00",
            workerId = "w1",
            clientId = "c1",
            location = "Dist 1, HCMC",
            estimatedPrice = 250000.0
        )
    )

    override suspend fun getJobs(): NetworkResult<List<Job>> {
        delay(1000)
        return NetworkResult.Success(mockJobs)
    }

    override suspend fun requestService(
        title: String,
        description: String,
        category: String,
        location: String
    ): NetworkResult<Job> {
        delay(1500)
        val newJob = Job(
            id = "j${mockJobs.size + 1}",
            title = title,
            description = description,
            status = JobStatus.PENDING,
            scheduledTime = "ASAP",
            workerId = null,
            clientId = "c1",
            location = location,
            estimatedPrice = null
        )
        mockJobs.add(newJob)
        return NetworkResult.Success(newJob)
    }

    override suspend fun updateJobStatus(jobId: String, status: String): NetworkResult<Job> {
        delay(500)
        val index = mockJobs.indexOfFirst { it.id == jobId }
        if (index != -1) {
            val updated = mockJobs[index].copy(status = JobStatus.valueOf(status))
            mockJobs[index] = updated
            return NetworkResult.Success(updated)
        }
        return NetworkResult.Error(com.fixmatch.mobile.domain.util.DataError.Api.NOT_FOUND)
    }
}

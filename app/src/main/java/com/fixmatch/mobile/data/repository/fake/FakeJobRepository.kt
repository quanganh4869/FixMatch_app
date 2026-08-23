package com.fixmatch.mobile.data.repository.fake

import com.fixmatch.mobile.domain.model.Job
import com.fixmatch.mobile.domain.model.JobStatus
import com.fixmatch.mobile.domain.repository.JobRepository
import com.fixmatch.mobile.domain.util.NetworkResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class FakeJobRepository : JobRepository {

    // Initialize with some past jobs, but no active job so we can test the full flow
    private val _jobsState = MutableStateFlow<List<Job>>(
        listOf(
            Job(
                id = "j_past_1",
                title = "Vệ sinh máy lạnh",
                description = "Bảo trì định kỳ 2 máy lạnh",
                status = JobStatus.REVIEWED,
                scheduledTime = "Hôm qua",
                workerId = "w1",
                clientId = "c1",
                location = "Quận 1, TP.HCM",
                estimatedPrice = 250000.0
            )
        )
    )

    override suspend fun getJobs(): NetworkResult<List<Job>> {
        delay(500)
        return NetworkResult.Success(_jobsState.value)
    }
    
    override fun observeJobs(): Flow<List<Job>> {
        return _jobsState
    }
    
    override fun observeJob(jobId: String): Flow<Job?> {
        return _jobsState.map { jobs -> jobs.find { it.id == jobId } }
    }

    override suspend fun requestService(
        title: String,
        description: String,
        category: String,
        location: String
    ): NetworkResult<Job> {
        delay(1000) // Simulate network
        val newJob = Job(
            id = "j${System.currentTimeMillis()}",
            title = title,
            description = description,
            status = JobStatus.CREATED,
            scheduledTime = "ASAP",
            workerId = null,
            clientId = "c1",
            location = location,
            estimatedPrice = null
        )
        
        val currentList = _jobsState.value.toMutableList()
        currentList.add(0, newJob) // Add to top
        _jobsState.value = currentList
        
        return NetworkResult.Success(newJob)
    }

    override suspend fun updateJobStatus(jobId: String, status: String): NetworkResult<Job> {
        delay(300)
        val currentList = _jobsState.value.toMutableList()
        val index = currentList.indexOfFirst { it.id == jobId }
        if (index != -1) {
            val updated = currentList[index].copy(status = JobStatus.valueOf(status))
            currentList[index] = updated
            _jobsState.value = currentList
            return NetworkResult.Success(updated)
        }
        return NetworkResult.Error(com.fixmatch.mobile.domain.util.DataError.Api.NOT_FOUND)
    }
    
    // MOCK SCENARIO METHODS - To be used by Debug Menu or UI for testing flow

    fun assignWorker(jobId: String, workerId: String, price: Double = 250000.0) {
        val currentList = _jobsState.value.toMutableList()
        val index = currentList.indexOfFirst { it.id == jobId }
        if (index != -1) {
            val updated = currentList[index].copy(
                status = JobStatus.WORKER_FOUND,
                workerId = workerId,
                estimatedPrice = price
            )
            currentList[index] = updated
            _jobsState.value = currentList
        }
    }
}

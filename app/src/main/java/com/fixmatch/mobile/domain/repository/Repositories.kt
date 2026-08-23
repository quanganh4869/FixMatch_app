package com.fixmatch.mobile.domain.repository

import com.fixmatch.mobile.domain.model.Job
import com.fixmatch.mobile.domain.model.Worker
import com.fixmatch.mobile.domain.util.NetworkResult
import kotlinx.coroutines.flow.Flow

interface WorkerRepository {
    suspend fun getWorkers(category: String?, page: Int, pageSize: Int): NetworkResult<List<Worker>>
    suspend fun getWorkerDetails(workerId: String): NetworkResult<Worker>
}

interface JobRepository {
    suspend fun getJobs(): NetworkResult<List<Job>>
    fun observeJobs(): Flow<List<Job>>
    fun observeJob(jobId: String): Flow<Job?>
    suspend fun requestService(title: String, description: String, category: String, location: String): NetworkResult<Job>
    suspend fun updateJobStatus(jobId: String, status: String): NetworkResult<Job>
}

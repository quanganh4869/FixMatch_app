package com.fixmatch.mobile.di

import com.fixmatch.mobile.data.repository.fake.FakeJobRepository
import com.fixmatch.mobile.domain.repository.JobRepository

import kotlinx.coroutines.flow.MutableStateFlow

/**
 * A simple Service Locator to provide shared mock state across the entire application.
 * In Phase 3, this will be replaced by Hilt/Koin and real repositories.
 */
object ServiceLocator {
    // Singleton instance of JobRepository to share state across screens
    val jobRepository: JobRepository by lazy {
        FakeJobRepository()
    }
    
    // Track the currently active job being booked/tracked by the user
    val currentActiveJobId = MutableStateFlow<String?>(null)
}

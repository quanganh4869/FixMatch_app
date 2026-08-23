package com.fixmatch.mobile.data.network

object NetworkConfig {
    // Single configurable base URL for the FastAPI backend.
    // Replace with staging/production URLs as needed via Build Variants or flavor config later.
    const val BASE_URL = "https://api.FixMatch.com/v1/"
    
    // Timeout values
    const val CONNECT_TIMEOUT = 30L
    const val READ_TIMEOUT = 30L
    const val WRITE_TIMEOUT = 30L
}

package com.fixmatch.mobile.domain.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.delay
import kotlin.math.*

data class LatLng(val latitude: Double, val longitude: Double)

interface LocationProvider {
    val currentLocation: Flow<LatLng?>
    suspend fun startTracking()
    fun stopTracking()
}

class MockLocationProvider(
    private val start: LatLng,
    private val end: LatLng,
    private val speedKmH: Double = 30.0,
    private val updateIntervalMs: Long = 1000
) : LocationProvider {
    
    private val _currentLocation = MutableStateFlow<LatLng?>(null)
    override val currentLocation: Flow<LatLng?> = _currentLocation.asStateFlow()
    
    private var isTracking = false
    
    override suspend fun startTracking() {
        isTracking = true
        _currentLocation.value = start
        
        val totalDistanceKm = calculateDistance(start, end)
        val timeNeededHours = totalDistanceKm / speedKmH
        val timeNeededMs = (timeNeededHours * 3600 * 1000).toLong()
        
        val steps = (timeNeededMs / updateIntervalMs).toInt()
        val latStep = (end.latitude - start.latitude) / steps
        val lngStep = (end.longitude - start.longitude) / steps
        
        var currentLat = start.latitude
        var currentLng = start.longitude
        
        for (i in 0..steps) {
            if (!isTracking) break
            currentLat += latStep
            currentLng += lngStep
            _currentLocation.value = LatLng(currentLat, currentLng)
            delay(updateIntervalMs)
        }
        
        if (isTracking) {
            _currentLocation.value = end
        }
    }
    
    override fun stopTracking() {
        isTracking = false
    }
    
    companion object {
        fun calculateDistance(point1: LatLng, point2: LatLng): Double {
            val R = 6371e3 // metres
            val phi1 = point1.latitude * Math.PI/180 // phi, lambda in radians
            val phi2 = point2.latitude * Math.PI/180
            val deltaPhi = (point2.latitude - point1.latitude) * Math.PI/180
            val deltaLambda = (point2.longitude - point1.longitude) * Math.PI/180

            val a = sin(deltaPhi/2) * sin(deltaPhi/2) +
                    cos(phi1) * cos(phi2) *
                    sin(deltaLambda/2) * sin(deltaLambda/2)
            val c = 2 * atan2(sqrt(a), sqrt(1-a))

            return (R * c) / 1000.0 // in km
        }
    }
}

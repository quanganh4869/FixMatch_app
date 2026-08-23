package com.fixmatch.mobile.domain.util

sealed interface DataError {
    enum class Network : DataError {
        REQUEST_TIMEOUT,
        NO_INTERNET,
        SERVER_UNAVAILABLE,
        UNKNOWN
    }
    
    enum class Api : DataError {
        BAD_REQUEST,          // 400
        UNAUTHORIZED,         // 401
        FORBIDDEN,            // 403
        NOT_FOUND,            // 404
        CONFLICT,             // 409
        VALIDATION_ERROR,     // 422
        TOO_MANY_REQUESTS,    // 429
        INTERNAL_SERVER_ERROR,// 500
        UNKNOWN
    }
}

fun DataError.toUserMessage(): String {
    return when(this) {
        DataError.Network.NO_INTERNET -> "No internet connection. Please check your network."
        DataError.Network.REQUEST_TIMEOUT -> "The request timed out. Please try again."
        DataError.Network.SERVER_UNAVAILABLE -> "The server is currently unavailable."
        DataError.Api.UNAUTHORIZED -> "Your session has expired. Please log in again."
        DataError.Api.FORBIDDEN -> "You do not have permission to perform this action."
        DataError.Api.NOT_FOUND -> "The requested resource was not found."
        DataError.Api.VALIDATION_ERROR -> "Please check your input and try again."
        DataError.Api.INTERNAL_SERVER_ERROR -> "An unexpected error occurred on our end."
        else -> "Something went wrong. Please try again later."
    }
}

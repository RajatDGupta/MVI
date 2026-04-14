package com.demo.core.common.util

import com.demo.core.common.resource.Resource

suspend fun <T> safeApiCall(
    apiCall: suspend () -> T
): Resource<T> {
    return try {
        Resource.Success(apiCall())
    } catch (e: Exception) {
        Resource.Error(
            message = e.message ?: "Something went wrong",
            throwable = e
        )
    }
}
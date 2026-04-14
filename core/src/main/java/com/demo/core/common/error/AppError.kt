package com.demo.core.common.error

sealed class AppError {
    object Network : AppError()
    object Unauthorized : AppError()
    object Unknown : AppError()

    data class Custom(val message: String) : AppError()
}
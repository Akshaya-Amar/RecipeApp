package com.example.firstkotlinrecipeproject.util

sealed class Response<T>(open val data: T? = null, open val message: String? = null) {
    data class Success<T>(override val data: T, override val message: String? = null) :
        Response<T>(data, message)

    data class Error<T>(override val message: String?, override val data: T? = null) :
        Response<T>(message = message, data = data)

    class Loading<T> : Response<T>(null, null)
}
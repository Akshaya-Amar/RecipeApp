package com.example.firstkotlinrecipeproject.util

sealed class Response<T>(data: T? = null, message: String? = null) {
    data class Success<T>(val data: T, val message: String? = null) : Response<T>(data, message)
    data class Error<T>(val message: String?, val data: T? = null) :
        Response<T>(message = message, data = data)
    class Loading<T> : Response<T>(null, null)
}
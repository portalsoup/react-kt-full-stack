package com.portalsoup.example.fullstack.utils

import java.lang.RuntimeException

/**
 * Usage example:
 *
 *      val x: Try<String> = ...
 *
 *      val str: String = when (x) {
 *          is Success -> x.data
 *          is Failure -> throw x.error
 */
sealed class Try<out T> {

    data class Success<out T>(
        val data: T
        ): Try<T>()

    data class Failure(
        val error: Throwable = TryFailedException("An operation failed")
    ): Try<Nothing>()

    fun isSuccess(): Boolean = when (this) {
        is Success -> true
        is Failure -> false
    }

    fun isFailure(): Boolean = when (this) {
        is Success -> false
        is Failure -> true
    }

    fun throwOnFailure(): Try<T> = when (this) {
        is Success -> Success(data)
        is Failure -> throw error
    }

    fun wrapException(wrapper: Throwable): Try<T> = when (this) {
        is Success<T> -> Success(data)
        is Failure -> Failure(wrapper.apply { addSuppressed(error) })
    }

    fun <R> map(transform: (T) -> R): Try<R> = when (this) {
        is Success -> {
            try {
                Success(transform(data))
            } catch (t: Throwable) {
                Failure(t)
            }
        }
        is Failure -> Failure(error)
    }
}

data class TryFailedException(val reason: String): RuntimeException(reason)
package com.dynasty.dynastyscansapp.data

import androidx.annotation.NonNull
import java.lang.Error

data class Resource<T>(val status: Int, val data: T?, val error: String? = null)  {

    fun isError() = status == STATUS_ERROR

    fun isSuccess() = status == STATUS_SUCCESS

    fun isLoading() = status == STATUS_LOADING

    companion object {

        const val STATUS_LOADING = 0

        const val STATUS_SUCCESS = 1

        const val STATUS_ERROR = -1

        /**
         * Helper method to create fresh state resource
         */
        fun <T> success(@NonNull data: T): Resource<T> {
            return Resource(STATUS_SUCCESS, data)
        }

        /**
         * Helper method to create error state Resources. Error state might also have the current data, if any
         */
        fun <T> error(message: String? = null): Resource<T> {
            return Resource(STATUS_ERROR, null, message)
        }

        /**
         * Helper methos to create loading state Resources.
         */
        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(STATUS_LOADING, data)
        }

    }
}
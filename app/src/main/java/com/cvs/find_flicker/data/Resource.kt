package com.cvs.find_flicker.data

data class Resource<out T>(val status: Status, val data: T?, val message: String?, val errorCode: Int?) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T): Resource<T> {
            return Resource(Status.SUCCESS, data, null, null)
        }

        fun <T> error(message: String, data: T? = null, errorCode: Int? = null): Resource<T> {
            return Resource(Status.ERROR, data, message, errorCode)
        }

        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(Status.LOADING, data, null, null)
        }
    }
}
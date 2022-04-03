package com.derek.test.service

import retrofit2.HttpException
import retrofit2.Response

@Throws(HttpException::class)
fun <T : Response<R>, R> T.checkIsSuccessful(): T {
    if (this.isSuccessful) {
        return this
    }
    throw  HttpException(this)
}

@Throws(EmptyBodyException::class)
fun <T : Response<R>, R> T.requireBody(): R {
    return this.body() ?: throw EmptyBodyException()
}


package com.derek.test.di

import com.derek.test.service.GitUserService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

private val OkHttpClientBase = named("okHttpClientBase")

val appModule = module {
    single { createGson() }
    single(OkHttpClientBase) { createOkHttpClientBase() }
    single { createOkHttpClientWithTimeout(get(OkHttpClientBase), TimeUnit.SECONDS.toMillis(60)) }
    single { provideGitUserService(provideGitRetrofit(get(), get())) }
}

private fun provideGitUserService(retrofit: Retrofit): GitUserService = retrofit.create()

private fun provideGitRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addCallAdapterFactory(RxJava3CallAdapterFactory.createWithScheduler(Schedulers.io()))
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .build()
}

private fun createOkHttpClientBase(): OkHttpClient {
    return OkHttpClient.Builder().apply {
        connectionSpecs(
            listOf(
                ConnectionSpec.COMPATIBLE_TLS,
                ConnectionSpec.CLEARTEXT
            )
        )
    }.build()
}

fun createGson(): Gson {
    return GsonBuilder()
        .serializeNulls()
        .setLenient()
        .create()
}


private fun createOkHttpClientWithTimeout(
    baseOkHttpClient: OkHttpClient,
    timeoutMilliseconds: Long
): OkHttpClient {
    return baseOkHttpClient.newBuilder()
        .connectTimeout(timeoutMilliseconds, TimeUnit.MILLISECONDS)
        .readTimeout(timeoutMilliseconds, TimeUnit.MILLISECONDS)
        .build()
}
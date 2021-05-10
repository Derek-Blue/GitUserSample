package com.derek.test

import android.app.Application
import com.derek.test.di.mainModule
import com.derek.test.di.presenterModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class GitUserApplication : Application() {

    companion object {
        lateinit var instance: GitUserApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        startKoin {
            androidLogger()
            androidContext(this@GitUserApplication)
            modules(
                listOf(
                    mainModule,
                    presenterModule
                )
            )
        }
    }
}
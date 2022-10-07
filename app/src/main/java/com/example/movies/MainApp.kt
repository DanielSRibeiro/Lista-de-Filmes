package com.example.movies

import android.app.Application
import com.example.movies.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MainApp  : Application(){

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.INFO)
            androidContext(this@MainApp)
            modules(DependencyModule.moduleApp)
        }
    }
}
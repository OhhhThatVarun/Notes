package com.varun.notes

import android.app.Application
import com.varun.notes.data.dataKoinModule
import com.varun.notes.presentation.uiKoinModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class NotesApp : Application() {

    override fun onCreate() {
        super.onCreate()

        // Dependency injection
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(applicationContext)
            modules(listOf(dataKoinModule, uiKoinModule))
        }

        // Logging
        Timber.plant(Timber.DebugTree())
    }
}
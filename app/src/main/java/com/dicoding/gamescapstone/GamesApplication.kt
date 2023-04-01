package com.dicoding.gamescapstone

import android.app.Application
import com.dicoding.core.di.listCoreModule
import com.dicoding.gamescapstone.di.listAppModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

@ExperimentalCoroutinesApi
@FlowPreview
open class GamesApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@GamesApplication)
            modules(
                listCoreModule + listAppModule
            )
        }
    }
}
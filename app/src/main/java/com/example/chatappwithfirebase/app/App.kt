package com.example.chatappwithfirebase.app

import android.app.Application
import com.example.chatappwithfirebase.di.appModule
import com.example.chatappwithfirebase.di.remoteModule
import com.example.chatappwithfirebase.di.viewModelModule
import org.koin.core.context.startKoin
import org.koin.dsl.koinApplication

class App: Application() {

    companion object {
        lateinit var instance: Application
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin {
            modules(listOf(appModule, viewModelModule, remoteModule))
        }
    }
}
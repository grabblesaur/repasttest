package com.example.bogdanov.repastapp.di

import android.app.Application

class Application : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = createComponent()
    }

    private fun createComponent(): AppComponent {
        return DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .build()
    }
}
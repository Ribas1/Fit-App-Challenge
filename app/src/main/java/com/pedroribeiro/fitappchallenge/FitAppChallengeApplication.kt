package com.pedroribeiro.fitappchallenge

import android.app.Application
import com.pedroribeiro.fitappchallenge.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class FitAppChallengeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@FitAppChallengeApplication)
            modules(
                networkModule,
                viewModelModule,
                repositoryModule,
                databaseModule,
                dataModule
            )
        }
    }
}
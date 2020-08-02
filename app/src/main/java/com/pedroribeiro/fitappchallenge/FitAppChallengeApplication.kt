package com.pedroribeiro.fitappchallenge

import android.app.Application
import com.pedroribeiro.fitappchallenge.di.networkModule
import com.pedroribeiro.fitappchallenge.di.repositoryModule
import com.pedroribeiro.fitappchallenge.di.viewModelModule
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
                repositoryModule
            )
        }
    }
}
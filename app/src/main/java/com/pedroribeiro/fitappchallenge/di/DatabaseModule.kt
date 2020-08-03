package com.pedroribeiro.fitappchallenge.di

import androidx.room.Room
import com.pedroribeiro.fitappchallenge.FitAppChallengeApplication
import com.pedroribeiro.fitappchallenge.db.FitAppChallengeDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

private const val DATABASE_NAME = "fit_app_challenge_db"

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            FitAppChallengeDatabase::class.java,
            DATABASE_NAME
        ).build()
    }
    single {
        get<FitAppChallengeDatabase>().goalsDao()
    }
}
package com.pedroribeiro.fitappchallenge.di

import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.HistoryClient
import com.google.android.gms.fitness.data.DataType
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val fitnessApiModule = module {
    single<FitnessOptions> {
        FitnessOptions.builder()
            .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
            .addDataType(DataType.AGGREGATE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
            .build()
    }
    /*
    single<GoogleSignInAccount> {
        GoogleSignIn.getAccountForExtension(
            androidContext(), get()
        )
    }
    single<HistoryClient> {
        Fitness.getHistoryClient(androidContext(), get())
    }
    */
}

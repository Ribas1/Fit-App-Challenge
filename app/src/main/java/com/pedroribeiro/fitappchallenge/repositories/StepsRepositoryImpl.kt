package com.pedroribeiro.fitappchallenge.repositories

import android.content.Context
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.data.Field
import com.google.android.gms.tasks.Tasks
import io.reactivex.Observable

class StepsRepositoryImpl(
    //wouldn't be necessary if google account could be injected through koin, since that would also allow injecting the history client
    private val context: Context,
    private val fitnessOptions: FitnessOptions
) : StepsRepository {

    //should be injected by koin
    private val googleAccount by lazy {
        GoogleSignIn.getAccountForExtension(
            context, fitnessOptions
        )
    }

    override fun getTodaysSteps(): Observable<String> {
        val fitnessHistoryClient = Fitness.getHistoryClient(context, googleAccount)
        val dailyTotalTask = fitnessHistoryClient
            .readDailyTotal(DataType.TYPE_STEP_COUNT_DELTA)
        return Observable.fromCallable { Tasks.await(dailyTotalTask) }
            .flatMap { dataSet -> Observable.fromIterable(dataSet.dataPoints) }
            .map { datapoint -> datapoint.getValue(Field.FIELD_STEPS).toString() }
    }
}
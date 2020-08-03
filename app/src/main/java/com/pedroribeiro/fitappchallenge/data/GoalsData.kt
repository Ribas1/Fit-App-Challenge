package com.pedroribeiro.fitappchallenge.data

import com.pedroribeiro.fitappchallenge.db.GoalsDao
import com.pedroribeiro.fitappchallenge.extensions.exceptionWhenEmpty
import com.pedroribeiro.fitappchallenge.model.GoalsResponse
import com.pedroribeiro.fitappchallenge.model.GoalsUiModel
import com.pedroribeiro.fitappchallenge.network.GoalsApi
import io.reactivex.Observable
import io.reactivex.Observable.concatArrayDelayError

class GoalsData(
    private val api: GoalsApi,
    private val goalsDao: GoalsDao
) {
    fun getGoals(): Observable<GoalsResponse> {
        val goalsFromApi = getGoalsFromApi()
        val goalsFromDb = getGoalsFromDb()
        return concatArrayDelayError(
            goalsFromApi,
            goalsFromDb
        ).onErrorResumeNext(goalsFromDb)
    }

    private fun getGoalsFromApi(): Observable<GoalsResponse> {
        return api.getGoals()
            .doOnSuccess {
                it.items.map { goal ->
                    goalsDao.insertGoals(goal)
                }
            }
            .toObservable()
    }

    private fun getGoalsFromDb(): Observable<GoalsResponse> {
        return goalsDao.getGoals()
            .exceptionWhenEmpty()
            .map { goals ->
                GoalsResponse(goals)
            }
            .toObservable()
    }

}

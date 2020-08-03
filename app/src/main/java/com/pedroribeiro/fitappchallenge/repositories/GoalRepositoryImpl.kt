package com.pedroribeiro.fitappchallenge.repositories

import com.pedroribeiro.fitappchallenge.data.GoalsData
import com.pedroribeiro.fitappchallenge.model.GoalsResponse
import com.pedroribeiro.fitappchallenge.model.GoalsUiModel
import io.reactivex.Observable

class GoalRepositoryImpl(
    private val goalsData: GoalsData
) : GoalRepository {
    override fun getGoals(): Observable<GoalsResponse> {
        return goalsData.getGoals()
    }

}

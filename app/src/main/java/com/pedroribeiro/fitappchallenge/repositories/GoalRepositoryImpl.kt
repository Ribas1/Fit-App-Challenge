package com.pedroribeiro.fitappchallenge.repositories

import android.util.Log
import com.pedroribeiro.fitappchallenge.model.GoalsResponse
import com.pedroribeiro.fitappchallenge.model.GoalsUiModel
import com.pedroribeiro.fitappchallenge.network.GoalsApi
import io.reactivex.Single

class GoalRepositoryImpl(
    private val api: GoalsApi
) : GoalRepository {
    override fun getGoal(): Single<GoalsUiModel> {
        return api.getGoals()
            .map { it.mapToUi() }
    }

}

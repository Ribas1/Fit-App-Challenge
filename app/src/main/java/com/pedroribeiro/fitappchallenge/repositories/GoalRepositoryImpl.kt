package com.pedroribeiro.fitappchallenge.repositories

import com.pedroribeiro.fitappchallenge.model.GoalsResponse
import com.pedroribeiro.fitappchallenge.network.GoalsApi
import io.reactivex.Single

class GoalRepositoryImpl(
    val api: GoalsApi
) : GoalRepository {
    override fun getGoal(): Single<GoalsResponse> {
        return api.getGoals()
    }

}

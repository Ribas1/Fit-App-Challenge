package com.pedroribeiro.fitappchallenge.repositories

import com.pedroribeiro.fitappchallenge.model.GoalsResponse
import io.reactivex.Single

interface GoalRepository {
    fun getGoal(): Single<GoalsResponse>
}

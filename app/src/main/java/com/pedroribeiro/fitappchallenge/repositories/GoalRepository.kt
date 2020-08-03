package com.pedroribeiro.fitappchallenge.repositories

import com.pedroribeiro.fitappchallenge.model.GoalsResponse
import com.pedroribeiro.fitappchallenge.model.GoalsUiModel
import io.reactivex.Single

interface GoalRepository {
    fun getGoal(): Single<GoalsUiModel>
}

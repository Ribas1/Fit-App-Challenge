package com.pedroribeiro.fitappchallenge.repositories

import com.pedroribeiro.fitappchallenge.model.GoalsResponse
import com.pedroribeiro.fitappchallenge.model.GoalsUiModel
import io.reactivex.Observable
import io.reactivex.Single

interface GoalRepository {
    fun getGoals(): Observable<GoalsResponse>
}

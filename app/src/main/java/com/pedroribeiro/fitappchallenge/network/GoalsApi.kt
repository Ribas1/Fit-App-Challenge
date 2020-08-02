package com.pedroribeiro.fitappchallenge.network

import com.pedroribeiro.fitappchallenge.model.GoalsResponse
import io.reactivex.Single
import retrofit2.http.GET

interface GoalsApi {

    @GET("goals")
    fun getGoals() : Single<GoalsResponse>
}
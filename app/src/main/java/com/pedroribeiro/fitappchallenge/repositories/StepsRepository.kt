package com.pedroribeiro.fitappchallenge.repositories

import io.reactivex.Observable
import io.reactivex.Single

interface StepsRepository {
    fun getTodaysSteps(): Observable<String>
}
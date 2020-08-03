package com.pedroribeiro.fitappchallenge.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pedroribeiro.fitappchallenge.common.BaseViewModel
import com.pedroribeiro.fitappchallenge.common.SingleLiveEvent
import com.pedroribeiro.fitappchallenge.model.Goal
import com.pedroribeiro.fitappchallenge.model.GoalsResponse
import com.pedroribeiro.fitappchallenge.repositories.GoalRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeViewModel(
    private val repository: GoalRepository
) : BaseViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<Unit>()
    val error: LiveData<Unit> = _error

    private val _goals = MutableLiveData<GoalsResponse>()
    val goals: LiveData<GoalsResponse> = _goals

    private val _navigation = SingleLiveEvent<Navigation>()
    val navigation: LiveData<Navigation> = _navigation

    fun getGoals() {
        compositeDisposable.add(
            repository.getGoal()
                .doOnSubscribe { _loading.postValue(true) }
                .doFinally { _loading.postValue(false) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    _goals.postValue(response)
                }, {
                    _error.postValue(Unit)
                })
        )
    }

    fun onGoalClick(goal: Goal) {
        _navigation.postValue(Navigation.ToGoalFragment(goal))
    }

    sealed class Navigation {
        data class ToGoalFragment(
            val goal: Goal
        ) : Navigation()
    }

}
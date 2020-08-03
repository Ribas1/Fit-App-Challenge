package com.pedroribeiro.fitappchallenge.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pedroribeiro.fitappchallenge.common.BaseViewModel
import com.pedroribeiro.fitappchallenge.common.SingleLiveEvent
import com.pedroribeiro.fitappchallenge.model.GoalUiModel
import com.pedroribeiro.fitappchallenge.model.GoalsUiModel
import com.pedroribeiro.fitappchallenge.network.EmptyListException
import com.pedroribeiro.fitappchallenge.repositories.GoalRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeViewModel(
    private val repository: GoalRepository
) : BaseViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<Errors>()
    val error: LiveData<Errors> = _error

    private val _goals = MutableLiveData<GoalsUiModel>()
    val goals: LiveData<GoalsUiModel> = _goals

    private val _navigation = SingleLiveEvent<Navigation>()
    val navigation: LiveData<Navigation> = _navigation

    fun getGoals() {
        compositeDisposable.add(
            repository.getGoals()
                .doOnSubscribe { _loading.postValue(true) }
                .doFinally { _loading.postValue(false) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { it.mapToUi() }
                .subscribe({ response ->
                    _goals.postValue(response)
                }, {
                    onGetGoalsError(it)
                })
        )
    }

    private fun onGetGoalsError(it: Throwable?) {
        if (it is EmptyListException) {
            _error.postValue(Errors.EmptyList)
        } else {
            _error.postValue(Errors.Other)
        }
    }

    fun onGoalClick(goal: GoalUiModel) {
        _navigation.postValue(Navigation.ToGoalFragment(goal))
    }

    sealed class Navigation {
        data class ToGoalFragment(
            val goal: GoalUiModel
        ) : Navigation()
    }

    sealed class Errors {
        object EmptyList : Errors()
        object Other : Errors()
    }

}
package com.pedroribeiro.fitappchallenge.goal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pedroribeiro.fitappchallenge.common.BaseViewModel
import com.pedroribeiro.fitappchallenge.common.SingleLiveEvent
import com.pedroribeiro.fitappchallenge.model.GoalUiModel
import com.pedroribeiro.fitappchallenge.network.NoStepsTodayException
import com.pedroribeiro.fitappchallenge.repositories.StepsRepository
import com.pedroribeiro.fitappchallenge.schedulers.SchedulerProvider

class GoalViewModel(
    private val stepsRepository: StepsRepository,
    private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {

    private val _goalProgress = MutableLiveData<Pair<Int, Boolean>>()
    val goalProgress: LiveData<Pair<Int, Boolean>> = _goalProgress

    private val _navigation = SingleLiveEvent<Navigation>()
    val navigation: LiveData<Navigation> = _navigation

    private val _error = MutableLiveData<Errors>()
    val errors: LiveData<Errors> = _error

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private var goal: GoalUiModel? = null

    fun setup(goal: GoalUiModel?) {
        this.goal = goal
    }

    fun getUserData(hasPermissions: Boolean) {
        if (hasPermissions) {
            compositeDisposable.add(
                stepsRepository.getTodaysSteps()
                    .doOnSubscribe { _loading.postValue(true) }
                    .doFinally { _loading.postValue(false) }
                    .subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .subscribe({ steps ->
                        onGetSteps(steps)
                    }, {
                        onGetStepsError(it)
                    })
            )
        }
    }

    private fun onGetStepsError(it: Throwable?) {
        when (it) {
            is NoStepsTodayException -> _error.postValue(Errors.NoSteps)
            else -> _error.postValue(Errors.Other)
        }
    }

    fun onBackClick() {
        _navigation.postValue(Navigation.Up)
    }

    private fun onGetSteps(steps: String?) {
        val goal = goal?.stepGoal
        val stepsInt = steps?.toInt()
        val progress = calculateProgress(stepsInt, goal)
        _goalProgress.postValue(Pair(first = progress, second = progress == 100))
    }

    private fun calculateProgress(stepsInt: Int?, goal: Int?): Int {
        return goal?.let {
            stepsInt?.times(100)?.div(it)
        } ?: 0
    }

    sealed class Navigation {
        object Up : Navigation()
    }

    sealed class Errors {
        object NoSteps : Errors()
        object Other : Errors()
    }

}

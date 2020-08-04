package com.pedroribeiro.fitappchallenge.goal

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pedroribeiro.fitappchallenge.common.BaseViewModel
import com.pedroribeiro.fitappchallenge.common.SingleLiveEvent
import com.pedroribeiro.fitappchallenge.repositories.StepsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GoalViewModel(
    private val stepsRepository: StepsRepository
) : BaseViewModel() {

    private val _navigation = SingleLiveEvent<Navigation>()
    val navigation: LiveData<Navigation> = _navigation

    private val _error = MutableLiveData<Unit>()
    val error: LiveData<Unit> = _error

    fun onBackClick() {
        _navigation.postValue(Navigation.Up)
    }

    fun getUserData(hasPermissions: Boolean) {
        if (hasPermissions) {
            compositeDisposable.add(
                stepsRepository.getTodaysSteps()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        //post value to fragment in order to populate user progress section
                    }, {
                        _error.postValue(Unit)
                    })
            )
        }
    }

    sealed class Navigation {
        object Up : Navigation()
    }

}

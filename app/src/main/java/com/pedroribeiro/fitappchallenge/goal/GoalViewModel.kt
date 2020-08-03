package com.pedroribeiro.fitappchallenge.goal

import androidx.lifecycle.LiveData
import com.pedroribeiro.fitappchallenge.common.BaseViewModel
import com.pedroribeiro.fitappchallenge.common.SingleLiveEvent

class GoalViewModel : BaseViewModel() {

    private val _navigation = SingleLiveEvent<Navigation>()
    val navigation: LiveData<Navigation> = _navigation

    fun onBackClick() {
        _navigation.postValue(Navigation.Up)
    }

    sealed class Navigation {
        object Up : Navigation()
    }

}

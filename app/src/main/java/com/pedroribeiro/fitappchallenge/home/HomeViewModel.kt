package com.pedroribeiro.fitappchallenge.home

import androidx.lifecycle.ViewModel
import com.pedroribeiro.fitappchallenge.repositories.GoalRepository

class HomeViewModel(
    private val repository: GoalRepository
) : ViewModel() {

}
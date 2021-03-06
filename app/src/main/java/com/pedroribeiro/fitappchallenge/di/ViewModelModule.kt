package com.pedroribeiro.fitappchallenge.di

import com.pedroribeiro.fitappchallenge.goal.GoalViewModel
import com.pedroribeiro.fitappchallenge.home.HomeViewModel
import org.koin.dsl.module

val viewModelModule = module {
    factory { HomeViewModel(get(), get()) }
    factory { GoalViewModel(get(), get()) }
}
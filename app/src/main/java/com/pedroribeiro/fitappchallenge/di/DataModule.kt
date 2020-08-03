package com.pedroribeiro.fitappchallenge.di

import com.pedroribeiro.fitappchallenge.data.GoalsData
import org.koin.dsl.module


val dataModule = module {
    single { GoalsData(get(), get()) }
}
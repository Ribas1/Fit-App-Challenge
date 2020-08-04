package com.pedroribeiro.fitappchallenge.di

import com.pedroribeiro.fitappchallenge.repositories.GoalRepository
import com.pedroribeiro.fitappchallenge.repositories.GoalRepositoryImpl
import com.pedroribeiro.fitappchallenge.repositories.StepsRepository
import com.pedroribeiro.fitappchallenge.repositories.StepsRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<GoalRepository> { GoalRepositoryImpl(get()) }
    single<StepsRepository> { StepsRepositoryImpl(get(), get()) }
}
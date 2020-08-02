package com.pedroribeiro.fitappchallenge.di

import com.pedroribeiro.fitappchallenge.repositories.GoalRepository
import com.pedroribeiro.fitappchallenge.repositories.GoalRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<GoalRepository>{ GoalRepositoryImpl(get()) }
}
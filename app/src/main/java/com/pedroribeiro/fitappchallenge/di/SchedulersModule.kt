package com.pedroribeiro.fitappchallenge.di

import com.pedroribeiro.fitappchallenge.schedulers.SchedulerProvider
import com.pedroribeiro.fitappchallenge.schedulers.SchedulerProviderImpl
import org.koin.dsl.module

val schedulerModule = module {
    single<SchedulerProvider>{ SchedulerProviderImpl() }
}
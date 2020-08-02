package com.pedroribeiro.fitappchallenge.di

import com.pedroribeiro.fitappchallenge.network.GoalsApi
import okhttp3.OkHttpClient
import org.koin.android.BuildConfig
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single { provideOkHttpClient() }
    single { provideRetrofit(get()) }
    single { provideBooksApi(get()) }
}

fun provideBooksApi(retrofit: Retrofit): GoalsApi =
    retrofit.create(GoalsApi::class.java)

fun provideOkHttpClient() = OkHttpClient().newBuilder().build()

fun provideRetrofit(okHttpClient: OkHttpClient) = Retrofit
    .Builder().baseUrl(com.pedroribeiro.fitappchallenge.BuildConfig.BASE_URL)
    .client(okHttpClient)
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .addConverterFactory(GsonConverterFactory.create()).build()
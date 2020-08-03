package com.pedroribeiro.fitappchallenge.extensions

import com.pedroribeiro.fitappchallenge.network.EmptyListException
import io.reactivex.Single

fun <T>Single<List<T>>.exceptionWhenEmpty(): Single<List<T>> {
    return this.flatMap {
        return@flatMap if (it.isEmpty()) {
            Single.error(EmptyListException())
        } else {
            Single.just(it)
        }
    }
}
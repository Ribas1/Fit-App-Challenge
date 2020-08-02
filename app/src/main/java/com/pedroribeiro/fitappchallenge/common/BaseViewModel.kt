package com.pedroribeiro.fitappchallenge.common

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel : ViewModel() {
    protected val compositeDisposable = CompositeDisposable()
}

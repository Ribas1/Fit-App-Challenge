package com.pedroribeiro.fitappchallenge.extensions

import android.view.View
import android.widget.Button
import android.widget.ProgressBar

fun ProgressBar.show(visible: Boolean) {
    if (visible)
        this.visibility = View.VISIBLE
    else
        this.visibility = View.GONE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}
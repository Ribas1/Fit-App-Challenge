package com.pedroribeiro.fitappchallenge.network

import java.io.IOException

class NoStepsTodayException : IOException() {
    override val message: String?
        get() = "No steps registered for today, start walking!"
}
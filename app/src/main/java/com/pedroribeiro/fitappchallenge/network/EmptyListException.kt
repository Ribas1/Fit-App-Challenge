package com.pedroribeiro.fitappchallenge.network

import java.io.IOException

class EmptyListException : IOException() {
    override val message: String?
        get() = "The goals list is empty"
}
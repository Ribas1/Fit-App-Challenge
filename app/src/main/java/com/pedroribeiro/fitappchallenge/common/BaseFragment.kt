package com.pedroribeiro.fitappchallenge.common

import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment

open class BaseFragment : Fragment() {
    protected fun navigateTo(directions: NavDirections) {
        NavHostFragment.findNavController(this).navigate(directions)
    }

    protected fun navigateBack() {
        NavHostFragment.findNavController(this).navigateUp()
    }
}

package com.pedroribeiro.fitappchallenge.utils

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import io.mockk.every

object LifecycleOwnerUtils {
    fun setupLifecycleOwner(lifecycleOwner: LifecycleOwner) {
        val lifecycle = LifecycleRegistry(lifecycleOwner)
        every { lifecycleOwner.lifecycle } returns lifecycle
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }
}
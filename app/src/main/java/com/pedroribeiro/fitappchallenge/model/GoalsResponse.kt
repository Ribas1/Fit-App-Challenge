package com.pedroribeiro.fitappchallenge.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


data class GoalsResponse(
    val items: List<Goal>
)

@Parcelize
data class Goal(
    val id: Long,
    val title: String,
    val description: String,
    val type: String,
    val goal: Long,
    val reward: Reward
) : Parcelable

@Parcelize
data class Reward(
    val trophy: String,
    val points: Int
) : Parcelable
package com.pedroribeiro.fitappchallenge.model


data class GoalsResponse(
    val items: List<Goal>
)

data class Goal(
    val id: Long,
    val title: String,
    val description: String,
    val type: String,
    val goal: Long,
    val reward: Reward
)

data class Reward(
    val trophy: String,
    val points: Int
)
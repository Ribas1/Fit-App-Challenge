package com.pedroribeiro.fitappchallenge.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class GoalsUiModel(
    val goals: List<GoalUiModel>
)

@Parcelize
data class GoalUiModel(
    val id: Long,
    val title: String,
    val description: String,
    val type: GoalType,
    val goal: Long,
    val reward: RewardUiModel
) : Parcelable

@Parcelize
data class RewardUiModel(
    val trophy: Trophy,
    val points: Int
) : Parcelable

enum class GoalType(value: String) {
    STEP("step"), WALKING("walking_distance"), RUNNING("running_distance"), OTHER("other")
}

enum class Trophy(value: String) {
    ZOMBIE("zombie_hand"), BRONZE("bronze_medal"), SILVER("silver_medal"), GOLD("gold_medal"), NONE("none")
}
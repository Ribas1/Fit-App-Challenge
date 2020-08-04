package com.pedroribeiro.fitappchallenge.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import com.pedroribeiro.fitappchallenge.R
import com.pedroribeiro.fitappchallenge.model.GoalType.*
import com.pedroribeiro.fitappchallenge.model.Trophy.*
import kotlinx.android.parcel.Parcelize

data class GoalsUiModel(
    val goals: List<GoalUiModel>
) {
    fun getUpdatedUiModel(): List<GoalUiModel> {
        goals.map {
            it.typeImage = getTypeImage(it.type)
            it.reward.trophyImage = getTrophyImage(it.reward.trophy)
        }
        return goals
    }

    private fun getTrophyImage(trophy: Trophy): Int? {
        return when (trophy) {
            ZOMBIE -> R.drawable.ic_zombie_medal
            BRONZE -> R.drawable.ic_bronze_medal
            SILVER -> R.drawable.ic_silver_medal
            GOLD -> R.drawable.ic_gold_medal
            NONE -> null
        }
    }

    private fun getTypeImage(type: GoalType): Int? {
        return when (type) {
            STEP -> R.drawable.ic_steps
            WALKING -> R.drawable.ic_walking
            RUNNING -> R.drawable.ic_running
            OTHER -> null
        }
    }
}

@Parcelize
data class GoalUiModel(
    val id: Long,
    val title: String,
    val description: String,
    val type: GoalType,
    val stepGoal: Int,
    val reward: RewardUiModel,
    @DrawableRes var typeImage: Int?
) : Parcelable

@Parcelize
data class RewardUiModel(
    val trophy: Trophy,
    val points: Int,
    @DrawableRes var trophyImage: Int?
) : Parcelable

enum class GoalType(val value: String) {
    STEP("step"), WALKING("walking_distance"), RUNNING("running_distance"), OTHER("other")
}

enum class Trophy(val value: String) {
    ZOMBIE("zombie_hand"), BRONZE("bronze_medal"), SILVER("silver_medal"), GOLD("gold_medal"), NONE("none")
}
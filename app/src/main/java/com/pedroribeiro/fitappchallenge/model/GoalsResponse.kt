package com.pedroribeiro.fitappchallenge.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


data class GoalsResponse(
    val items: List<Goal>
) {
    fun mapToUi(): GoalsUiModel {
        return GoalsUiModel(
            items.map {
                it.mapToUi()
            }
        )
    }
}

@Entity(tableName = "goals")
data class Goal(
    @PrimaryKey val id: Long,
    val title: String,
    val description: String,
    val type: String,
    val goal: Int,
    @Embedded val reward: Reward
) {
    fun mapToUi(): GoalUiModel {
        return GoalUiModel(
            id,
            title,
            description,
            mapTypeStringToEnum(type),
            goal,
            reward.mapToUi(),
            null
        )
    }

    private fun mapTypeStringToEnum(type: String): GoalType {
        return GoalType.values().firstOrNull { it.value == type } ?: GoalType.OTHER
    }
}

@Entity(tableName = "rewards")
data class Reward(
    @PrimaryKey(autoGenerate = true) val rewardId: Long,
    val trophy: String,
    val points: Int
) {
    fun mapToUi(): RewardUiModel {
        return RewardUiModel(
            mapTrophyStringToEnum(trophy),
            points,
            null
        )
    }

    private fun mapTrophyStringToEnum(trophy: String): Trophy {
        return Trophy.values().firstOrNull { it.value == trophy } ?: Trophy.NONE
    }
}
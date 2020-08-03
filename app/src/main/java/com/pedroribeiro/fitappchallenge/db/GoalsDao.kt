package com.pedroribeiro.fitappchallenge.db

import androidx.room.*
import com.pedroribeiro.fitappchallenge.model.Goal
import io.reactivex.Single

@Dao
interface GoalsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGoals(goals: List<Goal>): Single<List<Long>>

    @Query("SELECT * FROM goals")
    fun getGoals(): Single<List<Goal>>
}
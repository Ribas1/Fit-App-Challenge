package com.pedroribeiro.fitappchallenge.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pedroribeiro.fitappchallenge.model.Goal
import com.pedroribeiro.fitappchallenge.model.Reward

@Database(
    entities = [Goal::class, Reward::class],
    version = 1
)
abstract class FitAppChallengeDatabase : RoomDatabase() {
    abstract fun goalsDao(): GoalsDao

    companion object {

        @Volatile
        private var INSTANCE: FitAppChallengeDatabase? = null
        private const val DATABASE_NAME = "weather_app_challenge_db"

        fun getInstance(context: Context): FitAppChallengeDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context): FitAppChallengeDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                FitAppChallengeDatabase::class.java, DATABASE_NAME
            )
                .build()
        }
    }
}
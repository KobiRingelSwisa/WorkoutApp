package com.mykotlinapps.bodybuilder.data.local_db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context
import com.mykotlinapps.bodybuilder.data.ExerciseEntity
import com.mykotlinapps.bodybuilder.data.dao.ExerciseDao
import com.mykotlinapps.bodybuilder.utilities.Converters

@Database(entities = [ExerciseEntity::class], version = 4, exportSchema = false)  // Incremented version to 4
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun exerciseDao(): ExerciseDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "exercise_database"
                ).fallbackToDestructiveMigration() // This will destroy and rebuild the database if necessary
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

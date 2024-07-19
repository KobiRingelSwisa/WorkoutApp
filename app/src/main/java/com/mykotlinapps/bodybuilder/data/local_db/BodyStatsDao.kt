package com.mykotlinapps.bodybuilder.data.local_db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mykotlinapps.bodybuilder.data.BodyStats
import androidx.room.*

@Dao
interface BodyStatsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBodyStats(bodyStats: BodyStats): Long

    @Query("SELECT * FROM body_stats WHERE id = :id")
    suspend fun getBodyStats(id: Int): BodyStats?

    @Query("DELETE FROM body_stats")
    suspend fun deleteAll(): Int

    @Query("SELECT * FROM body_stats ORDER BY id DESC LIMIT 1")
    fun getLatestBodyStats(): LiveData<BodyStats>
}

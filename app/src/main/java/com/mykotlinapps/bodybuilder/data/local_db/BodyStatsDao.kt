package com.mykotlinapps.bodybuilder.data.local_db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mykotlinapps.bodybuilder.data.BodyStats

@Dao
interface BodyStatsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBodyStats(bodyStats: BodyStats)

    @Query("SELECT * FROM body_stats ORDER BY id DESC LIMIT 1")
    fun getLatestBodyStats(): LiveData<BodyStats>
}

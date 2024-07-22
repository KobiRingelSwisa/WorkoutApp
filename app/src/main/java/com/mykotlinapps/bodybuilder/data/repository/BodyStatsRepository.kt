package com.mykotlinapps.bodybuilder.data.repository

import androidx.lifecycle.LiveData
import com.mykotlinapps.bodybuilder.data.BodyStats
import com.mykotlinapps.bodybuilder.data.dao.BodyStatsDao

class BodyStatsRepository(private val bodyStatsDao: BodyStatsDao) {

    suspend fun insertBodyStats(bodyStats: BodyStats) {
        bodyStatsDao.insertBodyStats(bodyStats)
    }

    fun getLatestBodyStats(): LiveData<BodyStats> {
        return bodyStatsDao.getLatestBodyStats()
    }
}

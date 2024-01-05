package com.example.a7minutesworkout

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {
    @Insert
    suspend fun insert(historyEntity: HistoryEntity)

    @Update
    suspend fun update(historyEntity: HistoryEntity)

    @Delete
    suspend fun delete(historyEntity: HistoryEntity)

    @Query("SELECT * FROM `history-table`")
    fun fetchAllHistory() : Flow<List<HistoryEntity>>

    @Query("SELECT * FROM `history-table` WHERE date = :date")
    fun fetchHistoryByDate(date: String): Flow<List<HistoryEntity>>
}
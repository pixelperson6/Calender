package com.sample.calender.feature_reminder.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.sample.calender.feature_reminder.domain.model.Reminder
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {

    @Query("SELECT * FROM reminder WHERE id = :id")
    suspend fun getReminderById(id:Int) :Reminder?

    @Query("SELECT * FROM reminder WHERE date = :date")
    fun getRemindersByDate(date:String) : Flow<List<Reminder>>

    @Insert(onConflict = REPLACE)
    suspend fun insertReminder(reminder: Reminder)

    @Delete
    suspend fun deleteReminder(reminder:Reminder)
}
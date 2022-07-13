package com.sample.calender.feature_reminder.domain.repository

import com.sample.calender.feature_reminder.domain.model.Reminder
import kotlinx.coroutines.flow.Flow

interface ReminderRepository {


    suspend fun getReminderById(id:Int):Reminder?

    fun getRemindersByDate(date:String): Flow<List<Reminder>>

    suspend fun insertReminder(reminder: Reminder)

    suspend fun deleteReminder(reminder:Reminder)

}
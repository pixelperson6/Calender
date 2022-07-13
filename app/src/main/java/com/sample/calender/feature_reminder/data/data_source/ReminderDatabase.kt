package com.sample.calender.feature_reminder.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sample.calender.feature_reminder.domain.model.Reminder

@Database(
    entities = [Reminder::class],
    version = 1,
    exportSchema = false
)
abstract class ReminderDatabase:RoomDatabase() {
    abstract val reminderDao: ReminderDao

    companion object{
        const val DATABASE_NAME = "reminders_db"
    }
}
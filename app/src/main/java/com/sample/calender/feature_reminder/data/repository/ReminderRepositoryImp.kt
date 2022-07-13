package com.sample.calender.feature_reminder.data.repository


import com.sample.calender.feature_reminder.data.data_source.ReminderDao
import com.sample.calender.feature_reminder.domain.model.Reminder
import com.sample.calender.feature_reminder.domain.repository.ReminderRepository
import kotlinx.coroutines.flow.Flow

class ReminderRepositoryImp(
    private val dao: ReminderDao
) : ReminderRepository {

    override suspend fun getReminderById(id: Int): Reminder? {
        return dao.getReminderById(id)
    }

    override fun getRemindersByDate(date: String): Flow<List<Reminder>> {
        return dao.getRemindersByDate(date)
    }

    override suspend fun insertReminder(reminder: Reminder) {
        dao.insertReminder(reminder)
    }

    override suspend fun deleteReminder(reminder: Reminder) {
        dao.deleteReminder(reminder)
    }
}
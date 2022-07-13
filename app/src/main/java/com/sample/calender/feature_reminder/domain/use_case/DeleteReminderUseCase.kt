package com.sample.calender.feature_reminder.domain.use_case

import com.sample.calender.feature_reminder.domain.model.Reminder
import com.sample.calender.feature_reminder.domain.repository.ReminderRepository


class DeleteReminderUseCase(
    private val repository: ReminderRepository
) {
    suspend operator fun invoke(reminder: Reminder){
        repository.deleteReminder(reminder = reminder)
    }
}
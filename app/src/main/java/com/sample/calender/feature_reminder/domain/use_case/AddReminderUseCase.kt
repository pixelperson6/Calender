package com.sample.calender.feature_reminder.domain.use_case

import com.sample.calender.feature_reminder.domain.model.InvalidReminderException
import com.sample.calender.feature_reminder.domain.model.Reminder
import com.sample.calender.feature_reminder.domain.repository.ReminderRepository


class AddReminderUseCase(
    private val repository: ReminderRepository
) {
    @Throws(InvalidReminderException::class)
    suspend operator fun invoke(reminder: Reminder){
        if (reminder.date.isBlank()){
            throw InvalidReminderException("The date of the Reminder can't be empty.")
        }
        if (reminder.reminder.isBlank()){
            throw InvalidReminderException("The reminder text of the Reminder can't be empty.")
        }
        repository.insertReminder(reminder)
    }
}
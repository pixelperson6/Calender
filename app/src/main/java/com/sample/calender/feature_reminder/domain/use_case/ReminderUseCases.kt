package com.sample.calender.feature_reminder.domain.use_case

data class ReminderUseCases(
    val getReminders: GetRemindersUseCase,
    val deleteReminder: DeleteReminderUseCase,
    val addReminder: AddReminderUseCase,
    val getReminder: GetReminderUseCase
)

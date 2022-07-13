package com.sample.calender.feature_reminder.domain.use_case

import com.sample.calender.feature_reminder.domain.use_case.AddReminderUseCase
import com.sample.calender.feature_reminder.domain.use_case.DeleteReminderUseCase
import com.sample.calender.feature_reminder.domain.use_case.GetReminderUseCase

data class ReminderUseCases(
    val getReminders: GetRemindersUseCase,
    val deleteReminder: DeleteReminderUseCase,
    val addReminder: AddReminderUseCase,
    val getReminder: GetReminderUseCase
)

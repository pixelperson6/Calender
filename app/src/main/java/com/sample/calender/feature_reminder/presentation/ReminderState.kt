package com.sample.calender.feature_reminder.presentation

import com.sample.calender.feature_reminder.domain.model.Reminder
import com.sample.calender.feature_reminder.domain.util.OrderType
import com.sample.calender.feature_reminder.domain.util.ReminderOrder

data class ReminderState(
    val reminders:List<Reminder> = emptyList(),
    val reminderOrder: ReminderOrder = ReminderOrder.Date(OrderType.Descending),
    val isOrderSectionVisible:Boolean = false
)

package com.sample.calender.feature_reminder.domain.use_case


import com.sample.calender.feature_reminder.domain.model.Reminder
import com.sample.calender.feature_reminder.domain.repository.ReminderRepository
import com.sample.calender.feature_reminder.domain.util.OrderType
import com.sample.calender.feature_reminder.domain.util.ReminderOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetRemindersUseCase(
    private val repository: ReminderRepository
) {
    operator fun invoke(
        reminderOrder: ReminderOrder = ReminderOrder.Date(OrderType.Descending),date:String
    ): Flow<List<Reminder>>{

        return repository.getRemindersByDate(date).map { reminders ->
            when(reminderOrder.orderType){
                is OrderType.Ascending ->{
                    when(reminderOrder){
                        is ReminderOrder.Title -> reminders.sortedBy { it.reminder.lowercase() }
                        is ReminderOrder.Date -> reminders.sortedBy { it.timestamp }
                        is ReminderOrder.Color -> reminders.sortedBy { it.color }

                    }
                }
                is OrderType.Descending ->{
                    when(reminderOrder){
                        is ReminderOrder.Title -> reminders.sortedByDescending { it.reminder.lowercase() }
                        is ReminderOrder.Date -> reminders.sortedByDescending { it.timestamp }
                        is ReminderOrder.Color -> reminders.sortedByDescending { it.color }

                    }
                }
            }
        }
    }
}
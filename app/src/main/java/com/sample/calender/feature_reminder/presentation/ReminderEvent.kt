package com.sample.calender.feature_reminder.presentation

import androidx.compose.ui.focus.FocusState
import com.sample.calender.feature_reminder.domain.model.Reminder
import com.sample.calender.feature_reminder.domain.util.ReminderOrder

sealed class AddReminderEvent{
    data class EnteredReminder(val newReminder:String): AddReminderEvent()
    data class ChangeReminderFocus(val titleFocusState: FocusState): AddReminderEvent()
    data class ChangeColor(val color:Int):AddReminderEvent()
    object SaveReminder: AddReminderEvent()
    object CancelReminder:AddReminderEvent()
}

sealed class ReminderEvent{
    data class Order(val reminderOrder: ReminderOrder):ReminderEvent()
    data class DeleteReminder(val reminder : Reminder):ReminderEvent()
    object RestoreReminder:ReminderEvent()
    object ToggleOrderSection:ReminderEvent()
}

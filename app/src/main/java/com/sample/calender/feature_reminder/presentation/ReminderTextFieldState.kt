package com.sample.calender.feature_reminder.presentation

data class ReminderTextFieldState(
    val text:String = "",
    val hint:String = "Add reminder...",
    val isHintVisible:Boolean = true
)

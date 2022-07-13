package com.sample.calender.feature_reminder.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sample.calender.ui.theme.*

@Entity
data class Reminder(
    val date:String,
    val reminder:String,
    val timestamp:Long,
    val color:Int,
    @PrimaryKey val id:Int?=null
) {
    companion object {
        val reminderColors = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink)
    }
}

class InvalidReminderException(message:String):Exception(message)

package com.sample.calender.feature_reminder.domain.use_case

import com.sample.calender.feature_reminder.domain.model.Reminder
import com.sample.calender.feature_reminder.domain.repository.ReminderRepository

class GetReminderUseCase(
    private val repository: ReminderRepository
){
    suspend operator fun invoke(id:Int): Reminder?{
        return repository.getReminderById(id =id )
    }
}
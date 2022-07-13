package com.sample.calender.di

import android.app.Application
import androidx.room.Room
import com.sample.calender.feature_reminder.data.data_source.ReminderDatabase
import com.sample.calender.feature_reminder.data.repository.ReminderRepositoryImp
import com.sample.calender.feature_reminder.domain.repository.ReminderRepository
import com.sample.calender.feature_reminder.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideReminderDatabase(app:Application): ReminderDatabase {
        return Room.databaseBuilder(
            app,
            ReminderDatabase::class.java,
            ReminderDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideReminderRepository(db:ReminderDatabase): ReminderRepository {
        return ReminderRepositoryImp(db.reminderDao)
    }

    @Provides
    @Singleton
    fun provideReminderUseCases(repository: ReminderRepository): ReminderUseCases {
        return ReminderUseCases(
            getReminders = GetRemindersUseCase(repository),
            deleteReminder = DeleteReminderUseCase(repository),
            addReminder = AddReminderUseCase(repository),
            getReminder = GetReminderUseCase(repository)
        )
    }
}
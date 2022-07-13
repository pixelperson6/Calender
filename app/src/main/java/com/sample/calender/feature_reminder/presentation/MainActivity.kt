package com.sample.calender.feature_reminder.presentation

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.himanshoe.kalendar.common.KalendarSelector
import com.himanshoe.kalendar.common.KalendarStyle
import com.himanshoe.kalendar.ui.Kalendar
import com.himanshoe.kalendar.ui.KalendarType
import com.sample.calender.feature_reminder.presentation.components.ReminderEditMode
import com.sample.calender.feature_reminder.presentation.components.ReminderNormalMode
import com.sample.calender.ui.theme.CalenderTheme
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var mainViewModel:MainViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            mainViewModel = hiltViewModel()

            LaunchedEffect(key1 = true) {
                mainViewModel.changeCurrentDate(LocalDate.now().toString())
                mainViewModel.getReminders(mainViewModel.state.value.reminderOrder)
            }
            CalenderTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.surface
                ) {
                    Column(Modifier.fillMaxSize()) {
                        Kalendar(
                            kalendarType = KalendarType.Firey(),
                            kalendarStyle = KalendarStyle(
                                kalendarSelector = KalendarSelector.Circle()
                            ),

                            onCurrentDayClick = { day, _ ->
                                mainViewModel.changeCurrentDate(day.toString())
                                mainViewModel.changeEditMode(false)
                                mainViewModel.getReminders(mainViewModel.state.value.reminderOrder)
                            }, errorMessage = {
                                //Handle the error if any
                            })

                        if (mainViewModel.isEditMode.value) {
                            ReminderEditMode(
                                modifier = Modifier
                                    .fillMaxHeight(),
                                reminderColor = mainViewModel.reminderColor.value
                            )
                        } else {
                            ReminderNormalMode()
                        }
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        if(mainViewModel.isEditMode.value){
            mainViewModel.resetTextField()
            mainViewModel.changeEditMode(false)
        }else{
            super.onBackPressed()
        }
    }
}


package com.sample.calender.feature_reminder.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.calender.feature_reminder.domain.model.InvalidReminderException
import com.sample.calender.feature_reminder.domain.model.Reminder
import com.sample.calender.feature_reminder.domain.use_case.ReminderUseCases
import com.sample.calender.feature_reminder.domain.util.ReminderOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val reminderUseCases: ReminderUseCases
) : ViewModel() {

    private val _isEditMode = mutableStateOf(false)
    val isEditMode: State<Boolean> = _isEditMode

    fun changeEditMode(isEditMode:Boolean){
        _isEditMode.value = isEditMode
    }

    private val _reminder = mutableStateOf(ReminderTextFieldState())
    val reminder: State<ReminderTextFieldState> = _reminder

    fun resetTextField(){
        _reminder.value = ReminderTextFieldState()
    }

    private val _currentDate = mutableStateOf("")
    private val currentDate: State<String> = _currentDate

    fun changeCurrentDate(currentDate:String){
        _currentDate.value = currentDate
    }

    private val _currentId = mutableStateOf(-1)
    private val currentId: State<Int> = _currentId

    fun changeCurrentId(currentId:Int){
        _currentId.value = currentId
    }

    private val _reminderColor = mutableStateOf(Reminder.reminderColors.random().toArgb())
    val reminderColor: State<Int> = _reminderColor

    private var currentReminderId:Int? = null


    fun addEditReminder() {
            if (currentId.value != -1){
                viewModelScope.launch {
                    reminderUseCases.getReminder(currentId.value)?.also { reminderData ->
                        currentReminderId = reminderData.id
                        _reminder.value = reminder.value.copy(
                            text = reminderData.reminder,
                            isHintVisible = false
                        )
                        _reminderColor.value = reminderData.color
                    }
                }
            }
    }


    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    fun onEditEvent(event: AddReminderEvent) {
        when (event) {
            is AddReminderEvent.EnteredReminder -> {
                _reminder.value = reminder.value.copy(
                    text = event.newReminder
                )
            }
            is AddReminderEvent.ChangeReminderFocus -> {
                _reminder.value = reminder.value.copy(
                    isHintVisible = !event.titleFocusState.isFocused && reminder.value.text.isBlank()
                )
            }
            is AddReminderEvent.ChangeColor ->{
                _reminderColor.value = event.color
            }
            is AddReminderEvent.SaveReminder -> {
                viewModelScope.launch {
                    try {
                        reminderUseCases.addReminder(
                            Reminder(
                                date = currentDate.value,
                                reminder= reminder.value.text,
                                id = currentReminderId,
                                timestamp = System.currentTimeMillis(),
                                color = reminderColor.value,

                            )
                        )
                        _eventFlow.emit(UiEvent.SaveReminder)
                    } catch (e: InvalidReminderException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackBar(
                                message = e.message ?: "Couldn't save reminder"
                            )
                        )

                    }
                }

            }
            is AddReminderEvent.CancelReminder -> {
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.CancelReminder)
                }
            }
        }
    }

    private val _state = mutableStateOf(ReminderState())
    val state: State<ReminderState> = _state

    private var recentlyDeletedReminder:Reminder? = null

    private var getRemindersJob: Job? = null

    fun getReminders(reminderOrder: ReminderOrder) {
        getRemindersJob?.cancel()
        getRemindersJob = reminderUseCases.getReminders(reminderOrder = reminderOrder, date = currentDate.value).onEach { reminders ->
            _state.value = state.value.copy(
                reminders = reminders,
                reminderOrder = reminderOrder
            )
        }.launchIn(viewModelScope)

    }


    fun onReminderEvent(event: ReminderEvent){
        when(event){
            is ReminderEvent.DeleteReminder ->{
                viewModelScope.launch {
                    reminderUseCases.deleteReminder(event.reminder)
                    recentlyDeletedReminder = event.reminder
                }
            }
            is ReminderEvent.RestoreReminder ->{
                viewModelScope.launch {
                    reminderUseCases.addReminder(recentlyDeletedReminder ?: return@launch)
                    recentlyDeletedReminder = null

                }
            }
            is ReminderEvent.ToggleOrderSection ->{
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
            is ReminderEvent.Order ->{
                if (state.value.reminderOrder::class == event.reminderOrder::class &&
                    state.value.reminderOrder.orderType == event.reminderOrder.orderType){
                    return
                }
                getReminders(event.reminderOrder)
            }

        }

    }

    sealed class UiEvent {
        data class ShowSnackBar(val message: String) : UiEvent()
        object SaveReminder : UiEvent()
        object CancelReminder:UiEvent()
    }
}
package com.sample.calender.feature_reminder.presentation.components

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sample.calender.feature_reminder.domain.model.Reminder
import com.sample.calender.feature_reminder.presentation.AddReminderEvent
import com.sample.calender.feature_reminder.presentation.MainViewModel
import com.sample.calender.feature_reminder.presentation.ReminderEvent
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun ReminderEditMode(
    modifier: Modifier = Modifier,
    reminderColor: Int,
    viewModel: MainViewModel = hiltViewModel()
) {
    val reminderTextState = viewModel.reminder.value

    val reminderBackgroundAnimatable = remember {
        Animatable(
            Color(if (reminderColor != -1) reminderColor else viewModel.reminderColor.value)
        )
    }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is MainViewModel.UiEvent.ShowSnackBar -> {
                    SnackbarHostState().showSnackbar(
                        message = event.message
                    )
                }
                is MainViewModel.UiEvent.SaveReminder -> {
                    viewModel.changeEditMode(false)
                    viewModel.getReminders(viewModel.state.value.reminderOrder)
                }
                is MainViewModel.UiEvent.CancelReminder -> {
                    viewModel.changeEditMode(false)
                    viewModel.getReminders(viewModel.state.value.reminderOrder)
                }
            }
        }
    }

    val scope = rememberCoroutineScope()

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(reminderBackgroundAnimatable.value)
            .padding(8.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            TransparentHintTextField(
                text = reminderTextState.text,
                hint = reminderTextState.hint,
                onValueChange = {
                    viewModel.onEditEvent(AddReminderEvent.EnteredReminder(it))
                },
                onFocusChange = {
                    viewModel.onEditEvent(AddReminderEvent.ChangeReminderFocus(it))
                },
                isHintVisible = reminderTextState.isHintVisible,
                textStyle = MaterialTheme.typography.body1
            )
            Row(
                modifier = Modifier
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Reminder.reminderColors.forEach { color ->
                    val colorInt = color.toArgb()
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .shadow(15.dp, CircleShape)
                            .clip(CircleShape)
                            .background(color)
                            .border(
                                width = 3.dp,
                                color = if (viewModel.reminderColor.value == colorInt) {
                                    Color.Black
                                } else {
                                    Color.Transparent
                                },
                                shape = CircleShape
                            )
                            .clickable {
                                scope.launch {
                                    reminderBackgroundAnimatable.animateTo(
                                        targetValue = Color(colorInt),
                                        animationSpec = tween(
                                            durationMillis = 500
                                        )
                                    )
                                }
                                viewModel.onEditEvent(AddReminderEvent.ChangeColor(colorInt))
                            }
                    )

                }
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            IconButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.4f)
                    .clip(RoundedCornerShape(15.dp))
                    .background(MaterialTheme.colors.background),
                onClick = {
                    viewModel.onEditEvent(AddReminderEvent.SaveReminder)
                }) {
                Icon(
                    modifier = Modifier
                        .size(45.dp),
                    imageVector = Icons.Default.Done,
                    contentDescription = "Done",
                    tint = MaterialTheme.colors.primary
                )

            }
            Spacer(modifier = Modifier.height(8.dp))
            IconButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.4f)
                    .clip(RoundedCornerShape(15.dp))
                    .background(MaterialTheme.colors.background),

                onClick = {
                    viewModel.onEditEvent(AddReminderEvent.CancelReminder)
                }) {
                Icon(
                    modifier = Modifier
                        .size(45.dp),
                    imageVector = Icons.Default.Cancel,
                    contentDescription = "Cancel",
                    tint = MaterialTheme.colors.primary
                )

            }

        }

    }
}


@Composable
fun ReminderNormalMode(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel()
) {

    val state = viewModel.state.value
    val scope = rememberCoroutineScope()

    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.8f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (state.reminders.isEmpty()) {
                Text(
                    text = "No reminder today...",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.body1
                )
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Your Reminders",
                        style = MaterialTheme.typography.body1
                    )
                /*    IconButton(
                        onClick = {
                            viewModel.onReminderEvent(ReminderEvent.ToggleOrderSection)
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Sort,
                            contentDescription = "Sort",
                            tint = MaterialTheme.colors.primary
                        )
                    }*/
                }
             /*   AnimatedVisibility(
                    visible = state.isOrderSectionVisible,
                    enter = fadeIn() + slideInVertically(),
                    exit = fadeOut() + slideOutVertically()
                ) {
                    OrderSection(
                        modifier = Modifier
                            .fillMaxWidth(),
                        reminderOrder = state.reminderOrder,
                        onOrderChange = {
                            viewModel.onReminderEvent(ReminderEvent.Order(it))
                        })

                }*/
                Spacer(modifier = Modifier.height(16.dp))
                LazyColumn(
                ) {
                    items(state.reminders) { reminder ->
                        ReminderItem(
                            reminder = reminder,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.changeCurrentId(reminder.id ?: -1)
                                    viewModel.changeEditMode(true)
                                    viewModel.addEditReminder()
                                },
                            onDeleteClick = {
                                viewModel.onReminderEvent(ReminderEvent.DeleteReminder(reminder))
                                scope.launch {
                                    val result = SnackbarHostState().showSnackbar(
                                        message = "Reminder deleted",
                                        actionLabel = "Undo"
                                    )
                                    if (result == SnackbarResult.ActionPerformed) {
                                        viewModel.onReminderEvent(ReminderEvent.RestoreReminder)
                                    }
                                }
                            }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            IconButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f)
                    .clip(RoundedCornerShape(15.dp))
                    .background(MaterialTheme.colors.background),
                onClick = {
                    viewModel.changeCurrentId(-1)
                    viewModel.changeEditMode(true)
                    viewModel.resetTextField()
                    viewModel.addEditReminder()
                }) {

                Icon(
                    modifier = modifier.size(50.dp),
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    tint = MaterialTheme.colors.primary

                )

            }
        }


    }


}

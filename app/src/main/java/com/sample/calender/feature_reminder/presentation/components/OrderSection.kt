package com.sample.calender.feature_reminder.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sample.calender.feature_reminder.domain.util.OrderType
import com.sample.calender.feature_reminder.domain.util.ReminderOrder

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    reminderOrder: ReminderOrder = ReminderOrder.Date(OrderType.Descending),
    onOrderChange:(reminderOrder:ReminderOrder) -> Unit
) {

    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = "Title",
                selected = reminderOrder is ReminderOrder.Title,
                onSelected = { onOrderChange(ReminderOrder.Title(reminderOrder.orderType)) }
            )
            Spacer(modifier = Modifier.width(4.dp))
            DefaultRadioButton(
                text = "Date",
                selected = reminderOrder is ReminderOrder.Date,
                onSelected = { onOrderChange(ReminderOrder.Date(reminderOrder.orderType)) }
            )
            Spacer(modifier = Modifier.width(4.dp))
            DefaultRadioButton(
                text = "Color",
                selected = reminderOrder is ReminderOrder.Color,
                onSelected = { onOrderChange(ReminderOrder.Color(reminderOrder.orderType)) }
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = "Ascending",
                selected = reminderOrder.orderType is OrderType.Ascending,
                onSelected = { onOrderChange(reminderOrder.copy(OrderType.Ascending)) }
            )
            Spacer(modifier = Modifier.width(4.dp))
            DefaultRadioButton(
                text = "Descending",
                selected = reminderOrder.orderType is OrderType.Descending,
                onSelected = { onOrderChange(reminderOrder.copy(OrderType.Descending)) }
            )
        }
    }

}
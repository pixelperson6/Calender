package com.sample.calender.feature_reminder.domain.util

sealed class ReminderOrder(
    val orderType: OrderType
){
    class Title(orderType: OrderType): ReminderOrder(orderType = orderType)
    class Date(orderType: OrderType): ReminderOrder(orderType = orderType)
    class Color(orderType: OrderType): ReminderOrder(orderType = orderType)


    fun copy(orderType: OrderType): ReminderOrder {
        return when(this){
            is Title -> Title(orderType)
            is Date -> Date(orderType)
            is Color -> Color(orderType)
        }
    }
}

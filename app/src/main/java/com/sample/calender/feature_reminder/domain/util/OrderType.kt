package com.sample.calender.feature_reminder.domain.util

sealed class OrderType{
    object Ascending: OrderType()
    object Descending: OrderType()
}

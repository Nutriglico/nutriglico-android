package com.fiap.startupone.nutriglico.commons.ui

import android.content.Context
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

fun showDatePickerDialog(context: Context, onDateChange: (String) -> Unit) {
    val initialYear = LocalDate.now().year
    val initialMonth = LocalDate.now().monthValue - 1
    val initialDay = LocalDate.now().dayOfMonth

    android.app.DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val formattedDate = LocalDate.of(year, month + 1, dayOfMonth)
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            onDateChange(formattedDate)
        },
        initialYear,
        initialMonth,
        initialDay
    ).show()
}

fun showTimePickerDialog(context: Context, onTimeChange: (String) -> Unit) {
    val initialHour = LocalTime.now().hour
    val initialMinute = LocalTime.now().minute

    android.app.TimePickerDialog(
        context,
        { _, hour, minute ->
            val formattedTime = LocalTime.of(hour, minute)
                .format(DateTimeFormatter.ofPattern("HH:mm"))
            onTimeChange(formattedTime)
        },
        initialHour,
        initialMinute,
        true // formato 24 horas
    ).show()
}
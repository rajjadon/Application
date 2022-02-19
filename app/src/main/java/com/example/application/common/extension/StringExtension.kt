package com.example.application.common.extension

import com.example.application.common.utills.createAtDateFormatter
import com.example.application.common.utills.serverDateFormatter
import com.example.application.common.utills.utcCalendar
import timber.log.Timber

fun String.convertServerDateToAppDateString(): String =
    run {
        try {
            serverDateFormatter.parse(this)?.let { utcCalendar.time = it }
        } catch (e: Exception) {
            Timber.e(e)
        }
        createAtDateFormatter.format(utcCalendar.timeInMillis)
    }
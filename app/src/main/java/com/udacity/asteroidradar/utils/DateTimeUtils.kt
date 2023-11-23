package com.udacity.asteroidradar.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date

private const val DATE_FORMAT = "yyyy-MM-dd"

fun Date.convertDateToString(): String = SimpleDateFormat(DATE_FORMAT).format(this)

@RequiresApi(Build.VERSION_CODES.O)
fun String.toDate() = SimpleDateFormat(DATE_FORMAT).parse(this) ?: Date()

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDate.toDate(): Date = Date.from(this.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())


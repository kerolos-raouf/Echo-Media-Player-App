package com.example.echoplaymediaplayer.domain.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun getDateWithCustomFormat(millis : Long,format : String) : String{
    val sdf = SimpleDateFormat(format, Locale.getDefault())
    return sdf.format(Date(millis))
}
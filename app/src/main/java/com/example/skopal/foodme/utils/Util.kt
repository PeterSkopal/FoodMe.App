package com.example.skopal.foodme.utils

import java.text.SimpleDateFormat
import java.util.*

inline fun dateToSimpleString(date: Date) : String {
    val format = SimpleDateFormat("dd/MM/yyy")
    return format.format(date)
}

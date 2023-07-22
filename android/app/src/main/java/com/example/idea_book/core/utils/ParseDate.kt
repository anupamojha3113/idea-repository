package com.example.idea_book.core.utils

import java.text.SimpleDateFormat
import java.util.*

fun String.timeAgo(): String {
    val now = System.currentTimeMillis()

    // Parse the date string into a Date object
    val date = this.toDate()

    // Calculate the difference in milliseconds between now and the date
    return when(val difference = now - date.time) {
        in 0..1000 -> "just now"
        in 1000..60_000 -> "${difference / 1000} seconds ago"
        in 60_000..3_600_000 -> "${difference / 60_000} minutes ago"
        in 3_600_000..86_400_000 -> "${difference / 3_600_000} hours ago"
        in 86_400_000..604_800_000 -> "${difference / 86_400_000} days ago"
        in 604_800_000..2_419_200_000 -> "${difference / 604_800_000} weeks ago"
        in 2_419_200_000..3_154_000_000 -> "${difference / 2_419_200_000} months ago"
        else -> "${difference / 3_154_000_000} years ago"
    }
}

fun String.prettyPrintDate(): String {
    val date = this.toDate()
    val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    return dateFormat.format(date)
}

fun String.toDate(): Date {
    // format: 2022-10-15T15:43:54.646+05:30
    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.getDefault())
    format.timeZone = TimeZone.getTimeZone("UTC")
    return format.parse(this) ?: Date()
}

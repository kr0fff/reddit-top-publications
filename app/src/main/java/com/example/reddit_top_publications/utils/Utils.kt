package com.example.reddit_top_publications.utils

import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import java.util.Date
import java.util.concurrent.TimeUnit

fun Double.toRelativeTime(): String {
    val createdTime = Date(this.toLong() * 1000)
    val now = Date()
    val diffInMillis = now.time - createdTime.time
    val hoursAgo = TimeUnit.MILLISECONDS.toHours(diffInMillis)
    return "$hoursAgo hours ago"
}
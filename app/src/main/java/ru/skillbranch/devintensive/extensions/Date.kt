package ru.skillbranch.devintensive.extensions

import java.lang.IllegalStateException
import java.text.SimpleDateFormat
import java.time.Duration
import java.util.*
import kotlin.math.abs

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = MINUTE * 60
const val DAY = 24 * HOUR


enum class TimeUnits {
    SECOND {
        override fun plural(value: Int) = when (value % 10) {
            1 -> "$value секунду"
            in 2..4 -> "$value секунды"
            else -> "$value секунд"
        }
    },
    MINUTE {
        override fun plural(value: Int) = when (value % 10) {
            1 -> "$value минуту"
            in 2..4 -> "$value минуты"
            else -> "$value минут"
        }
    },
    HOUR {
        override fun plural(value: Int) = when (value % 10) {
            1 -> "$value час"
            in 2..4 -> "$value часа"
            else -> "$value часов"
        }
    },
    DAY {
        override fun plural(value: Int) = when (value % 10) {
            1 -> "$value день"
            in 2..4 -> "$value дня"
            else -> "$value дней"
        }


    };

    abstract fun plural(value: Int): String
}

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND): Date {
    var time = this.time
    time += when (units) {
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }
    this.time = time
    return this
}


fun Date.humanizeDiff(date: Date = Date()): String {
    val timeDiff = date.time - this.time
    val absTimeDiff = abs(timeDiff)
    val timeString = when {
        timeDiff < -360 * DAY -> "более чем через год"
        timeDiff < -26 * HOUR -> "через " + TimeUnits.DAY.plural((absTimeDiff / DAY).toInt())
        timeDiff < -22 * HOUR -> "через день"
        timeDiff < -75 * MINUTE -> "через " + TimeUnits.HOUR.plural((absTimeDiff / HOUR).toInt())
        timeDiff < -45 * MINUTE -> "через час"
        timeDiff < -75 * SECOND -> "через " + TimeUnits.MINUTE.plural((absTimeDiff / MINUTE).toInt())
        timeDiff < -45 * SECOND -> "через минуту"
        timeDiff < -1 * SECOND -> "через несколько секунд"
        timeDiff < SECOND -> "только что"
        timeDiff < 45 * SECOND -> "несколько секунд назад"
        timeDiff < 75 * SECOND -> "минуту назад"
        timeDiff < 45 * MINUTE -> TimeUnits.MINUTE.plural((absTimeDiff / MINUTE).toInt()) + " назад"
        timeDiff < 75 * MINUTE -> "час назад"
        timeDiff < 22 * HOUR -> TimeUnits.HOUR.plural((absTimeDiff / HOUR).toInt())  + " назад"
        timeDiff < 26 * HOUR -> "день назад"
        timeDiff < 360 * DAY -> TimeUnits.DAY.plural((absTimeDiff / DAY).toInt()) + " назад"
        else -> "более года назад"
    }

    return timeString
}

fun Date.shortFormat(): String {
    val pattern = if(this.isSameDay(Date())) "HH:mm" else "dd.MM.yy"
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.isSameDay(date: Date): Boolean {
    val day1 = this.time / DAY
    val day2 = date.time / DAY
    return day1 == day2
}
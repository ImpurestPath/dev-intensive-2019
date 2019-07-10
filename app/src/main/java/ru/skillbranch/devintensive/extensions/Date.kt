package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

enum class TimeUnits {
    SECOND,
    MINUTE,
    HOUR,
    DAY;

    fun plural(number: Int): String {
        val name: Array<String> = when (this) {
            SECOND -> arrayOf("секунду", "секунды", "секунд")
            MINUTE -> arrayOf("минуту", "минуты", "минут")
            HOUR -> arrayOf("час", "часа", "часов")
            DAY -> arrayOf("день", "дня", "дней")
        }
        val nameFin: String

        if (number in 0..20) {
            nameFin = when (number) {
                1 -> name[0]
                in 2..4 -> name[1]
                else -> name[2]
            }
            return "$number $nameFin"
        }

        val numberString: String = number.toString()
        val last2digits = numberString.drop(numberString.length - 2)

        if (last2digits.toInt() in 0..20) {
            nameFin = when (last2digits.toInt()) {
                1 -> name[0]
                in 2..4 -> name[1]
                else -> name[2]
            }
            return "$number $nameFin"
        }
        val last1Digit = last2digits.drop(1).toInt()

        nameFin = when (last1Digit) {
            1 -> name[0]
            in 2..4 -> name[1]
            else -> name[2]
        }

        return "$number $nameFin"
    }
}

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnits): Date {
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
    val diff = (this.time - date.time)
    var timeString = " "

    if (diff in -75000..75000) {
        when (diff / 1000) {
            in -1..1 -> return "только что"
            in 2..45 -> return "через несколько секунд"
            in -45..-2 -> return "несколько секунд назад"
            in -75..-46 -> return "минуту назад"
            in 46..75 -> return "через минуту"
        }
    }

    if (diff in -2759000..2759000) {
        val intDiffMin: Int = (diff.toInt()) / 1000 / 60

        if (intDiffMin >= -20 || intDiffMin <= 20) {
            when (intDiffMin) {
                in -20..-5 -> timeString = "минут"
                in -4..-2 -> timeString = "минуты"
                in -1..1 -> timeString = "минуту"
                in 2..4 -> timeString = "минуты"
                in 5..20 -> timeString = "минут"
            }
        }

        if (intDiffMin in -45..-21 || intDiffMin in 21..45) {
            val toMinutes = intDiffMin / 1000 / 60
            val lastCharMinutes: Char = toMinutes.toString().last()
            timeString = when ((lastCharMinutes.toString().toInt())) {
                1 -> "минуту"
                in 2..4 -> "минуты"
                else -> "минут"
            }
        }

        return if (intDiffMin > 0) {
            "через $intDiffMin $timeString"
        } else {
            ("${intDiffMin.toString().drop(1)} $timeString назад")
        }
    }

    if (diff in -4559000..4559000) {
        return if (diff > 0) {
            "через час"
        } else {
            "час назад"
        }
    }

    if (diff in -96199999..-4560000 || diff in 4560000..96199999) {
        val toHours: Int = ((diff / 3600).toInt()) / 1000
        when (toHours) {
            in -26..-23 -> return "день назад"
            -22 -> timeString = "часа"
            -21 -> timeString = "час"
            in -20..-5 -> timeString = "часов"
            in -4..-2 -> timeString = "часа"
            in -1..1 -> timeString = "час"
            in 2..4 -> timeString = "часа"
            in 5..20 -> timeString = "часов"
            21 -> timeString = "час"
            22 -> timeString = "часа"
            in 23..26 -> return "через день"
        }
        return if (toHours > 0) {
            "через $toHours $timeString"
        } else {
            "${toHours.toString().drop(1)} $timeString назад"
        }
    }

    val numLen: Int
    var toDays = (((diff / 3600) / 1000) / 24).toInt()
    val lastTwoOfThree: Int
    val lastofTwo: Int
    var moreThenZero = true

    if (diff <= -97200000 || diff >= 97200000) {

        if (toDays < 0) {
            numLen = toDays.toString().drop(1).length
            toDays = toDays.toString().drop(1).toInt()
            moreThenZero = false
        } else {
            numLen = toDays.toString().length
        }
        if (toDays > 360) {
            return if (moreThenZero) {
                "более чем через год"
            } else {
                "более года назад"
            }
        }

        if (toDays.toString().drop(1) == "00") {
            timeString = "дней"
            return if (moreThenZero) {
                "через $toDays $timeString"
            } else {
                "$toDays $timeString назад"
            }
        }

        if (numLen == 1) {
            timeString = when (toDays) {
                1 -> "день"
                in 2..4 -> "дня"
                else -> "дней"
            }
            return if (moreThenZero) {
                "через $toDays $timeString"
            } else {
                "$toDays $timeString назад"
            }
        }
        if (numLen == 2) {
            lastofTwo = toDays.toString().drop(1).toInt()
            lastTwoOfThree = toDays

            if (lastTwoOfThree in 1..20) {
                when (lastTwoOfThree) {
                    1 -> timeString = "день"
                    in 2..4 -> timeString = "дня"
                    in 5..20 -> timeString = "дней"
                }

                return if (moreThenZero) {
                    "через $toDays $timeString"
                } else {
                    "$toDays $timeString назад"
                }
            }
            timeString = when (lastofTwo) {
                1 -> "день"
                in 2..4 -> "дня"
                else -> "дней"
            }
            return if (moreThenZero) {
                "через $toDays $timeString"
            } else {
                "$toDays $timeString назад"
            }
        }

        if (numLen == 3) {
            lastTwoOfThree = toDays.toString().drop(1).toInt()
            if (lastTwoOfThree in 0..20) {
                when (lastTwoOfThree) {
                    0 -> timeString = "дней"
                    1 -> timeString = "день"
                    in 2..4 -> timeString = "дня"
                    in 5..20 -> timeString = "дней"
                }
                return if (moreThenZero) {
                    "через $toDays $timeString"
                } else {
                    "$toDays $timeString назад"
                }
            }
            lastofTwo = lastTwoOfThree.toString().drop(1).toInt()

            timeString = when (lastofTwo) {
                1 -> "день"
                in 2..4 -> "дня"
                else -> "дней"
            }
            return if (moreThenZero) {
                "через $toDays $timeString"
            } else {
                "$toDays $timeString назад"
            }

        }

        return "$toDays $timeString"
    } else {
        return (diff / 3600).toString()
    }
}
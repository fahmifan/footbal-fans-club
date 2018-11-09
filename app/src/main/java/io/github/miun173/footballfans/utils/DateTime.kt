package io.github.miun173.footballfans.utils

import java.util.*

object DateTime {
    fun convertDate(date: String): Calendar {
        try {
            val dateChar = date.split("-")
            println("dateChar >>> $dateChar")

            val newDate = GregorianCalendar()
            newDate.set(dateChar[0].toInt(), dateChar[1].toInt(), dateChar[2].toInt())
            return newDate

        } catch (e: Exception) {
            e.printStackTrace()

            return GregorianCalendar()
        }
    }

    fun getShortDate(date: String): String {
        val newDate = convertDate(date)

        return "${newDate.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault())}, " +
                "${newDate.get(Calendar.DATE)}/" +
                "${newDate.get(Calendar.MONTH)}/" +
                "${newDate.get(Calendar.YEAR)}"
    }
}

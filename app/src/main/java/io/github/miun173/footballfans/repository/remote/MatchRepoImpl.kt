package io.github.miun173.footballfans.repository.remote

import com.google.gson.Gson
import io.github.miun173.footballfans.model.Event
import io.github.miun173.footballfans.model.Events
import io.github.miun173.footballfans.model.Team
import io.github.miun173.footballfans.model.Teams
import java.net.URL
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class MatchRepoImpl: MatchRepo {
    override fun getTeam(name: String): List<Team> {
        val res = Gson().fromJson(
                URL(TheSportDbRoute.getTeam(name)).readText(),
                Teams::class.java)

        return res.teams ?: emptyList()
    }

    override fun getEventDetail(id: Int): List<Event> {
        val detail = Gson().fromJson(
                URL(TheSportDbRoute.getEventDetail(id)).readText(),
                Events::class.java)

        println("events: >>> ${detail.events}")

        detail.events?.map {
            val date = it.dateEvent?.let { it1 -> convertDate(it1) }
            it.dateEvent = "${date?.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault())}, " +
                    "${date?.get(Calendar.DATE)} " +
                    "${date?.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault())} " +
                    "${date?.get(java.util.Calendar.YEAR)}"
        }

        return detail.events ?: emptyList()
    }

    override fun getLast15Events(id: Int): List<Event> {
        val res = Gson().fromJson(
                URL(TheSportDbRoute.getLast15Events(id.toString())).readText(),
                Events::class.java)

        res.events?.map {
            val date = it.dateEvent?.let { it1 -> convertDate(it1) }
            it.dateEvent = "${date?.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault())}, " +
                    "${date?.get(Calendar.DATE)} " +
                    "${date?.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault())} " +
                    "${date?.get(java.util.Calendar.YEAR)}"
        }

        return res.events ?: emptyList()
    }

    override fun getNext15Events(id: Int): List<Event> {
        val res = Gson().fromJson(
                URL(TheSportDbRoute.getNext15Events(id.toString())).readText(),
                Events::class.java)

        res.events?.map {
            val date = it.dateEvent?.let { it1 -> convertDate(it1) }
            it.dateEvent = "${date?.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault())}, " +
                    "${date?.get(Calendar.DATE)} " +
                    "${date?.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault())} " +
                    "${date?.get(java.util.Calendar.YEAR)}"
        }

        return res.events ?: emptyList()
    }

    private fun convertDate(date: String): Calendar {
        val dateChar = date.split("-")
        val newDate = GregorianCalendar()
        newDate.set(dateChar[0].toInt(), dateChar[1].toInt(), dateChar[2].toInt())
        return newDate
    }


    private fun formatDate(date: String, format: String): String {
        var result = ""
        val old = SimpleDateFormat("yyyy-MM-dd")

        try {
            val oldDate = old.parse(date)
            val newFormat = SimpleDateFormat(format)

            result = newFormat.format(oldDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return result
    }

    fun getShortDate(date: String): String {
        return formatDate(date, "dd MMMM yyyy")
    }

}
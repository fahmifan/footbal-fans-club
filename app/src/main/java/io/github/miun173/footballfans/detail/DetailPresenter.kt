package io.github.miun173.footballfans.detail

import com.google.gson.Gson
import io.github.miun173.footballfans.model.Events
import io.github.miun173.footballfans.model.Teams
import io.github.miun173.footballfans.repository.Fetch
import io.github.miun173.footballfans.repository.TheSportDbRoute
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.util.*


class DetailPresenter(private val view: DetailContract.View,
                      private val fetch: Fetch)
    : DetailContract.Presenter {

    override fun getEventDetail(eventID: Int?) {
        doAsync {
            val detail = Gson().fromJson(
                    fetch.doReq(TheSportDbRoute.getEventDetail(eventID)),
                    Events::class.java
            )

            detail.events?.map {
                val date = it.dateEvent?.let { it1 -> convertDate(it1) }
                it.dateEvent = "${date?.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault())}, " +
                        "${date?.get(Calendar.DATE)} " +
                        "${date?.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault())} " +
                        "${date?.get(java.util.Calendar.YEAR)}"

                it.strAwayGoalDetails = splitEnterCutString(it.strAwayGoalDetails, ";")
                it.strAwayLineupGoalkeeper = splitEnterCutString(it.strAwayLineupGoalkeeper, ";")
                it.strAwayLineupDefense = splitEnterCutString(it.strAwayLineupDefense , ";")
                it.strAwayLineupMidfield = splitEnterCutString(it.strAwayLineupMidfield, ";")
                it.strAwayLineupForward = splitEnterCutString(it.strAwayLineupForward, ";")

                it.strHomeGoalDetails = splitEnterCutString(it.strHomeGoalDetails, ";")
                it.strHomeLineupDefense = splitEnterCutString(it.strHomeLineupDefense, ";")
                it.strHomeLineupMidfield = splitEnterCutString(it.strHomeLineupMidfield, ";")
                it.strHomeLineupForward = splitEnterCutString(it.strAwayLineupForward, ";")
                it.strHomeLineupGoalkeeper = splitEnterCutString(it.strHomeLineupGoalkeeper, ";")
            }

            uiThread {
                view.setEventDetail(detail.events?.get(0))
            }
        }
    }

    override fun getTeam(homeName: String?, awayName: String?) {
        doAsync {
            val homeTeam =  Gson().fromJson(
                    fetch.doReq(TheSportDbRoute.getTeam(homeName)),
                    Teams::class.java)

            val awayTeam = Gson().fromJson(
                    fetch.doReq(TheSportDbRoute.getTeam(awayName)),
                    Teams::class.java)

            uiThread {
                println("homeTeam >>> $homeTeam")
                println("awayTeam >>> $awayTeam")
                view.setLogo(homeTeam.teams?.get(0)?.teamBadge, awayTeam.teams?.get(0)?.teamBadge)
            }
        }
    }

    private fun splitEnterCutString(str: String?, delimiters: String): String {
        val seq = str?.split(delimiters)
        var newString = ""
        for(i in 0..((seq?.size)?.minus(1) ?:  0 ?: 0)) {
            newString += (seq?.get(i)?: "").trim() + "\n"
        }

        return newString
    }

    private fun convertDate(date: String): Calendar {
        val dateChar = date.split("-")
        val newDate = GregorianCalendar()
        newDate.set(dateChar[0].toInt(), dateChar[1].toInt(), dateChar[2].toInt())
        return newDate
    }

}
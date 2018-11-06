package io.github.miun173.footballfans.detail

import com.google.gson.Gson
import io.github.miun173.footballfans.model.Event
import io.github.miun173.footballfans.model.Events
import io.github.miun173.footballfans.model.Teams
import io.github.miun173.footballfans.repository.local.DBManager
import io.github.miun173.footballfans.repository.remote.Fetch
import io.github.miun173.footballfans.repository.remote.TheSportDbRoute
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.util.*

class DetailPresenter(private val view: DetailContract.View,
                      private val fetch: Fetch,
                      private val db: DBManager)
    : DetailContract.Presenter {

    override fun setFaved(event: Event?) {
        println("setFaved >>> begin")

        // get the faved data if exist
        val matchFav = db.getFav(event?.idEvent?.toInt() ?: -1)

        // if not exist (an emptyList) create one
        if(matchFav.isEmpty()) {
            event?.let {
                val newID = db.insertFavMatch(it)

                if(newID.equals(-1)) {
                    // show failure message
                    view.showSetFavFailed()
                    println("unable to unfav match")

                    return
                }

                view.showSetFavSuccess()
                view.showFav(true)
            }

            return
        }

        println("matchFav ID >>> ${matchFav[0].id}")

        // delete it!!
        val deletedID = db.deleteFavMatch(matchFav[0].id?.toInt() ?: -1)

        if(deletedID.equals(-1)) {
            // show failure message
            view.showUnsetFavFailed()
            println("unable to unset fav match")
            return
        }

        println("deletedID >>> $deletedID")

        view.showUnsetSuccess()
        view.showFav(false)
    }

    override fun getEventDetail(eventID: Int?) {
        doAsync {
            // read if match is faved (saved in DB)
            val showFav = eventID?.let { db.checkFav(it) } ?: false

            val detail = Gson().fromJson(
                    fetch.doReq(TheSportDbRoute.getEventDetail(eventID)),
                    Events::class.java)

            detail.events?.map {
                val date = it.dateEvent?.let { it1 -> convertDate(it1) }
                    it.dateEvent = "${date?.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault())}, " +
                        "${date?.get(Calendar.DATE)} " +
                        "${date?.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault())} " +
                        "${date?.get(java.util.Calendar.YEAR)}"
            }

            uiThread {
                view.setEventDetail(detail.events?.get(0))
                view.showFav(showFav)
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

    private fun convertDate(date: String): Calendar {
        val dateChar = date.split("-")
        val newDate = GregorianCalendar()
        newDate.set(dateChar[0].toInt(), dateChar[1].toInt(), dateChar[2].toInt())
        return newDate
    }

}
package io.github.miun173.footballfans.matchdetail

import io.github.miun173.footballfans.model.Event
import io.github.miun173.footballfans.repository.local.MatchLocal
import io.github.miun173.footballfans.repository.remote.MatchRemote
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MatchDetailPresenter(private val view: MatchDetailContract.View,
                           private val match: MatchRemote,
                           private val db: MatchLocal)
    : MatchDetailContract.Presenter {

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
            val detail = eventID.let { match.getEventDetail(it?:0) }

            println("detail >>> $detail")

            uiThread {
                view.setEventDetail(detail)
                view.showFav(showFav)
            }
        }
    }

    override fun getTeam(homeName: String?, awayName: String?) {
        doAsync {
            val homeTeam = homeName?.let { match.getTeams(it) }
            val awayTeam = awayName?.let { match.getTeams(it) }

            uiThread {
                if(homeTeam?.isEmpty() == true || awayTeam?.isEmpty() == true) {
                    view.showEmptyEvent(true)

                    return@uiThread
                }

                println("homeTeam >>> $homeTeam")
                println("awayTeam >>> $awayTeam")
                view.setLogo(homeTeam?.get(0)?.teamBadge, awayTeam?.get(0)?.teamBadge)
            }
        }
    }
}
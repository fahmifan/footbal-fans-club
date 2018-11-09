package io.github.miun173.footballfans.detail

import io.github.miun173.footballfans.model.Event
import io.github.miun173.footballfans.repository.local.DBManager
import io.github.miun173.footballfans.repository.remote.Fetch
import io.github.miun173.footballfans.repository.remote.MatchRepo
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class DetailPresenter(private val view: DetailContract.View,
                      private val fetch: Fetch,
                      private val match: MatchRepo,
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
            val detail = eventID.let { match.getEventDetail(it?:0) }

            println("detail >>> $detail")

            uiThread {
                view.setEventDetail(detail[0])
                view.showFav(showFav)
            }
        }
    }

    override fun getTeam(homeName: String?, awayName: String?) {
        doAsync {
            val homeTeam = homeName?.let { match.getTeam(it) }
            val awayTeam = awayName?.let { match.getTeam(it) }

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
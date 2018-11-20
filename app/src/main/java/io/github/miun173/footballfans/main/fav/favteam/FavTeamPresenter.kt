package io.github.miun173.footballfans.main.fav.favteam

import io.github.miun173.footballfans.repository.local.TeamLocal
import io.github.miun173.footballfans.repository.remote.MatchRemote
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class FavTeamPresenter(private val view: FavTeamContract.View,
                       private val matchRemote: MatchRemote,
                       private val teamLocal: TeamLocal): FavTeamContract.Presenter {

    override fun getFavmatch() {
        view.showLoading(true)

        // get fav from teamLocal
        val favs = teamLocal.getFavs()

        // if empty show no fav message
        if(favs.isEmpty()) {
            view.showNoFav()
            return
        }

        // else, get match from API
        doAsync {
            val teams = favs.map {
                it.teamID?.toInt()?.let { it1 -> matchRemote.getTeamDetail(it1) }
            }

            uiThread {
                println("team details >>> $teams")

                // then, show fav list
                view.setFavmatch(teams)
            }
        }
    }

}
package io.github.miun173.footballfans.main.fav.favmatch

import io.github.miun173.footballfans.repository.local.MatchLocal
import io.github.miun173.footballfans.repository.remote.MatchRemote
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class FavMatchPresenter(private val view: FavMatchContract.View,
                        private val matchRemote: MatchRemote,
                        private val db: MatchLocal): FavMatchContract.Presenter {
    override fun getFavmatch() {
        view.showLoading(true)

        // get fav from db
        val favs = db.getFavs()

        // if empty show no fav message
        if(favs.isEmpty()) {
            view.showNoFav()
            return
        }

        // else, get match from API
        doAsync {
            val events2 = favs.map {
                it.matchID?.toInt()?.let { it1 -> matchRemote.getEventDetail(it1) }
            }

            uiThread {
                // then, show fav list
                view.setFavmatch(events2)
            }
        }
    }

}
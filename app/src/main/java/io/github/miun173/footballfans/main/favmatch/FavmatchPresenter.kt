package io.github.miun173.footballfans.main.favmatch

import io.github.miun173.footballfans.repository.local.DBManager
import io.github.miun173.footballfans.repository.remote.MatchRepo
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class FavmatchPresenter(private val view: FavmatchContract.View,
                        private val matchRepo: MatchRepo,
                        private val db: DBManager): FavmatchContract.Presenter {
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
                it.matchID?.toInt()?.let { it1 -> matchRepo.getEventDetail(it1) }
            }

            uiThread {
                // then, show fav list
                view.setFavmatch(events2)
            }
        }
    }

}
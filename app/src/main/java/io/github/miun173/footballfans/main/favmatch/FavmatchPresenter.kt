package io.github.miun173.footballfans.main.favmatch

import com.google.gson.Gson
import io.github.miun173.footballfans.model.Events
import io.github.miun173.footballfans.repository.local.DBManager
import io.github.miun173.footballfans.repository.remote.Fetch
import io.github.miun173.footballfans.repository.remote.TheSportDbRoute
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class FavmatchPresenter(val view: FavmatchContract.View,
                        val fetch: Fetch,
                        val db: DBManager): FavmatchContract.Presenter {
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
            val events = favs.map {
                var res = Gson().fromJson(fetch.doReq(
                    TheSportDbRoute.getEventDetail(it.matchID?.toInt())),
                    Events::class.java
                )

                res.events?.get(0)
            }

            uiThread {
                // then, show fav list
                view.setFavmatch(events)
            }
        }
    }

}
package io.github.miun173.footballfans.detail

import com.google.gson.Gson
import io.github.miun173.footballfans.model.Teams
import io.github.miun173.footballfans.repository.Fetch
import io.github.miun173.footballfans.repository.TheSportDbRoute
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread



class DetailPresenter(private val view: DetailContract.View,
                      private val fetch: Fetch)
    : DetailContract.Presenter {

    override fun getTeam(homeName: String?, awayName: String?) {
        view.setHeader()
        view.setGoals()
        view.setLineups()

        doAsync {
            val homeTeam =  Gson().fromJson(
                    fetch.doReq(TheSportDbRoute.getTeam(homeName)),
                    Teams::class.java
            )

            val awayTeam = Gson().fromJson(
                    fetch.doReq(TheSportDbRoute.getTeam(awayName)),
                    Teams::class.java
            )

            uiThread {
                println("homeTeam >>> $homeTeam")
                println("awayTeam >>> $awayTeam")
                view.setLogo(homeTeam.teams?.get(0)?.teamBadge, awayTeam.teams?.get(0)?.teamBadge)
            }
        }
    }
}
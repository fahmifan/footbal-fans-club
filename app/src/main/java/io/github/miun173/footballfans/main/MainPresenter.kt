package io.github.miun173.footballfans.main

import com.google.gson.Gson
import io.github.miun173.footballfans.model.Events
import io.github.miun173.footballfans.model.Teams
import io.github.miun173.footballfans.repository.Fetch
import io.github.miun173.footballfans.repository.TheSportDbRoute
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainPresenter(private val view: MainContract.View,
                    private val fetch: Fetch,
                    private val gson: Gson)
    :  MainContract.Presenter {

    fun getTeamsLeague(league: String?) {
        view.showLoading()

        doAsync {
            val data = gson.fromJson(
                    fetch.doReq(TheSportDbRoute.getTeams(league)),
                    Teams::class.java)

            uiThread {
                view.hideLoading()

                println("teams: " + data.teams.toString())

                view.showTeamList(data.teams)
            }
        }
    }

    override fun getEvents(id: String?) {
        view.showLoading()

        doAsync {
            val res = gson.fromJson(
                    fetch.doReq(TheSportDbRoute.getLast15Events(id)),
                    Events::class.java)

            uiThread {
                view.hideLoading()
                println("events:" + res.events.toString())

                // i don't know what this is do
                res.events?.let { its -> view.showEvents(its) }
            }
        }
    }
}
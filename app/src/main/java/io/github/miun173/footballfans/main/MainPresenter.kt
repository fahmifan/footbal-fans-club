package io.github.miun173.footballfans.main

import com.google.gson.Gson
import io.github.miun173.footballfans.model.EventsRes
import io.github.miun173.footballfans.model.Team
import io.github.miun173.footballfans.model.TeamResponse
import io.github.miun173.footballfans.repository.ApiRepo
import io.github.miun173.footballfans.repository.TheSportDbRoute
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainPresenter(private val view: MainContract.View,
                    private val apiRepo: ApiRepo,
                    private val gson: Gson) {

    fun getTeamsLeague(league: String?) {
        view.showLoading()

        doAsync {
            val data = gson.fromJson(
                    apiRepo.doReq(TheSportDbRoute.getTeams(league)),
                    TeamResponse::class.java)

            uiThread {
                view.hideLoading()

                println("teams: " + data.teams.toString())

                view.showTeamList(data.teams)
            }
        }
    }

    fun getEvents(id: String?) {
        view.showLoading()

        doAsync {
            val res = gson.fromJson(
                    apiRepo.doReq(TheSportDbRoute.getLast15Events(id)),
                    EventsRes::class.java)

            uiThread {
                view.hideLoading()
                println("events:" + res.events.toString())

                // i don't know what this is do
                res.events?.let { its -> view.showEvents(its) }
            }
        }
    }
}
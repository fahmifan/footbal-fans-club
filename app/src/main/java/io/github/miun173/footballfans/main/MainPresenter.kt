package io.github.miun173.footballfans.main

import com.google.gson.Gson
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
                    TeamResponse::class.java
            )

            uiThread {
                view.hideLoading()

                println("teams: " + data.teams.toString())

                view.showTeamList(data.teams)
            }
        }
    }
}
package io.github.miun173.footballfans.main.team

import io.github.miun173.footballfans.repository.remote.MatchRepo
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class TeamPresenter(val  view: TeamContract.View,
                    val matchRepo: MatchRepo): TeamContract.Presenter {
    override fun getTeamsLeague(id: Int) {
        view.showLoading(true)

        doAsync {
            val res = matchRepo.getTeamsLeague(id)

            uiThread {

                if(!res.isEmpty()) {
                    view.setListTeam(res)
                    view.setTeamEmpty(false)
                    view.showLoading(false)
                } else {
                    view.setTeamEmpty(true)
                    view.showLoading(false)
                }
            }
        }
    }

    override fun getLeagues() {
        doAsync {
            val leagues = matchRepo.getLeagues()
            uiThread {
                if(!leagues.isEmpty()) {
                    view.setSpinner(leagues)
                }
            }
        }
    }

}
package io.github.miun173.footballfans.main.team

import io.github.miun173.footballfans.repository.remote.MatchRemote
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class TeamPresenter(val  view: TeamContract.View,
                    val matchRemote: MatchRemote): TeamContract.Presenter {
    override fun getTeamsLeague(id: Int) {
        view.showLoading(true)

        doAsync {
            val res = matchRemote.getTeamsLeague(id)

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
        view.showLoading(true)

        doAsync {
            val leagues = matchRemote.getLeagues()
            uiThread {
                if(!leagues.isEmpty()) {
                    view.setSpinner(leagues)
                }

                view.showLoading(false)
            }
        }
    }

}
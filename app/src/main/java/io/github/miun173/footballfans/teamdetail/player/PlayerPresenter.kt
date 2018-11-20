package io.github.miun173.footballfans.teamdetail.player

import io.github.miun173.footballfans.repository.remote.MatchRemote
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class PlayerPresenter(val view: PlayerContract.View,
                      val matchRemote: MatchRemote): PlayerContract.Presenter {

    override fun getTeamPlayers(teamName: String) {
        view.setLoading(true)

        doAsync {
            val players = matchRemote.getPlayers(teamName)

            uiThread {
                if(players.isEmpty()) {
                    view.setLoading(false)
                    view.setEmptyPlayers(true)
                    return@uiThread
                }

                view.setPlayers(players)
                view.setLoading(false)
                view.setEmptyPlayers(false)
            }
        }
    }

}
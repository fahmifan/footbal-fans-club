package io.github.miun173.footballfans.teamdetail.player

import android.util.Log
import io.github.miun173.footballfans.repository.remote.MatchRepo
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class PlayerPresenter(val view: PlayerContract.View,
                      val matchRepo: MatchRepo): PlayerContract.Presenter {

    override fun getTeamPlayers(teamName: String) {
        view.setLoading(true)

        doAsync {
            val players = matchRepo.getPlayers(teamName)

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
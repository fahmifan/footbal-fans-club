package io.github.miun173.footballfans.teamdetail.player

import io.github.miun173.footballfans.model.Player

interface PlayerContract {
    interface View {
        fun setLoading(show: Boolean)
        fun setPlayers(players: List<Player>)
        fun setEmptyPlayers(show: Boolean)
    }

    interface Presenter {
        fun getTeamPlayers(teamName: String)
    }
}
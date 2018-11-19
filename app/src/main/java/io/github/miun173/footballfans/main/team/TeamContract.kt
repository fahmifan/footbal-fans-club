package io.github.miun173.footballfans.main.team

import io.github.miun173.footballfans.model.League
import io.github.miun173.footballfans.model.Team

interface TeamContract {
    interface View {
        fun showLoading(show: Boolean)
        fun setListTeam(teams: List<Team>)
        fun setTeamEmpty(show: Boolean)
        fun setSpinner(leagues: List<League>)
    }

    interface Presenter {
        fun getTeamsLeague(id: Int)
        fun getLeagues()
    }
}
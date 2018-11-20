package io.github.miun173.footballfans.teamdetail

import io.github.miun173.footballfans.model.Team

interface TeamDetailContract {
    interface View {
        fun showFav(show: Boolean)
        fun showSetFavFailed()
        fun showUnsetFavFailed()
        fun showUnsetSuccess()
        fun showSetFavSuccess()
    }

    interface Presenter {
        fun setFaved(team: Team)
        fun getFaved(teamID: Int)
    }
}
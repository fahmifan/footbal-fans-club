package io.github.miun173.footballfans.main.fav.favteam

import io.github.miun173.footballfans.model.Team

interface FavTeamContract {
    interface  View {
        fun setFavmatch(teams: List<Team?>)
        fun showLoading(show: Boolean)
        fun showNoFav()
        fun showFavFailed()
    }

    interface Presenter {
        fun getFavmatch()
    }
}
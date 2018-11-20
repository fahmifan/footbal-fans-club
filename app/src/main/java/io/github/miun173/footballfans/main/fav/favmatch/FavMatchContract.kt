package io.github.miun173.footballfans.main.fav.favmatch

import io.github.miun173.footballfans.model.Event

interface FavMatchContract {
    interface  View {
        fun setFavmatch(event: List<Event?>)
        fun showLoading(show: Boolean)
        fun showNoFav()
        fun showFavFailed()
    }

    interface Presenter {
        fun getFavmatch()
    }
}
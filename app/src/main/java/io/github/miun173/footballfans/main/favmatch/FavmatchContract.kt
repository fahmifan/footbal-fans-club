package io.github.miun173.footballfans.main.favmatch

import io.github.miun173.footballfans.model.Event

interface FavmatchContract {
    interface  View {
        fun setFavmatch(event: List<Event?>)
        fun showLoading(show: Boolean)
        fun showNoFav()
    }

    interface Presenter {
        fun getFavmatch()
    }
}
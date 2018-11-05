package io.github.miun173.footballfans.main

import io.github.miun173.footballfans.model.Event

interface FavmatchContract {
    interface  View {
        fun setFavmatch(event: Event)
    }

    interface Presenter {
        fun getFavmatch()
    }
}
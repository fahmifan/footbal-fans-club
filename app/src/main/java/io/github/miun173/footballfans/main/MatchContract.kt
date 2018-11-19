package io.github.miun173.footballfans.main

import io.github.miun173.footballfans.model.League

interface MatchContract {
    interface View {
        fun setSpinner(leagues: List<League>)
    }

    interface Presenter {
        fun getSpinner()
    }
}
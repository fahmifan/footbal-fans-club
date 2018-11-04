package io.github.miun173.footballfans.main

import io.github.miun173.footballfans.model.Event
import io.github.miun173.footballfans.model.Team

interface MainContract {
    interface View {
        fun showLoading()
        fun hideLoading()
        fun showTeamList(teams: List<Team>?)
        fun showEvents(events: List<Event>)
    }

    interface Presenter {
        fun getNext15Events(id: String?)
        fun getLast15Events(id: String?)
    }
}
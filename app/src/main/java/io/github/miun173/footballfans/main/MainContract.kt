package io.github.miun173.footballfans.main

import io.github.miun173.footballfans.model.Team

interface MainContract {
    interface View {
        fun showLoading()
        fun hideLoading()
        fun showTeamList(teams: List<Team>?)
    }
}
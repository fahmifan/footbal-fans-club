package io.github.miun173.footballfans.searchedteam

import io.github.miun173.footballfans.model.Team

interface SearchedTeamContract {
    interface View {
        fun setResult(teams: List<Team>)
        fun setLoading(show: Boolean)
        fun setEmptyResult(show: Boolean)
    }

    interface Presenter {
        fun search(query: String)
    }

}
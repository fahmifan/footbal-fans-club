package io.github.miun173.footballfans.main.searchresult

import io.github.miun173.footballfans.model.Event

interface SearchedMatchContract {
    interface View {
        fun setResult(events: List<Event>)
        fun setLoading(show: Boolean)
        fun setEmptyResult(show: Boolean)
    }

    interface Presenter {
        fun search(query: String)
    }
}
package io.github.miun173.footballfans.matchdetail

import io.github.miun173.footballfans.model.Event

interface MatchDetailContract {
    interface View {
        fun setEventDetail(event: Event?)
        fun setLogo(homeBadge: String?, awayBadge: String?)
        fun showFav(show: Boolean)
        fun showLoading(show: Boolean)
        fun showSetFavFailed()
        fun showUnsetFavFailed()
        fun showUnsetSuccess()
        fun showSetFavSuccess()
        fun showEmptyEvent(show: Boolean)
    }

    interface Presenter {
        fun getTeam(homeName: String?, awayName: String?)
        fun getEventDetail(eventID: Int?)
        fun setFaved(event: Event?)
    }
}
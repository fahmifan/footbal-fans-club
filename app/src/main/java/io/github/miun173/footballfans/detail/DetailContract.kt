package io.github.miun173.footballfans.detail

import io.github.miun173.footballfans.model.Event

interface DetailContract {
    interface View {
        fun setEventDetail(event: Event?)
        fun setLogo(homeBadge: String?, awayBadge: String?)
        fun showFav(show: Boolean)
        fun showSetFavFailed()
        fun showUnsetFavFailed()
        fun showUnsetSuccess()
        fun showSetFavSuccess()
    }

    interface Presenter {
        fun getTeam(homeName: String?, awayName: String?)
        fun getEventDetail(eventID: Int?)
        fun setFaved(event: Event?)
    }
}
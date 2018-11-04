package io.github.miun173.footballfans.detail

import io.github.miun173.footballfans.model.Event

interface DetailContract {
    interface View {
        fun setEventDetail(event: Event?)
        fun setLogo(homeBadge: String?, awayBadge: String?)
    }

    interface Presenter {
        fun getTeam(homeName: String?, awayName: String?)
        fun getEventDetail(eventID: Int?)
    }
}
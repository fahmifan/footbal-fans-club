package io.github.miun173.footballfans.main

import io.github.miun173.footballfans.model.Event

interface ScheduleContract {
    interface SchedulerView {
        fun showLoading(show: Boolean)
        fun showEvents(events: List<Event>)
    }

    interface Presenter {
        fun getEvents(id: String?, isNext: Boolean)
    }
}
package io.github.miun173.footballfans.main.schedule

import io.github.miun173.footballfans.model.Event
import io.github.miun173.footballfans.model.League

interface ScheduleContract {
    interface SchedulerView {
        fun showLoading(show: Boolean)
        fun showEvents(events: List<Event>)
        fun setSpinner(leagues: List<League>)
    }

    interface Presenter {
        fun getEvents(id: Int, isNext: Boolean)
    }
}
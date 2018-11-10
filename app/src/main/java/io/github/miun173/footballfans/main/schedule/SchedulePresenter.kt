package io.github.miun173.footballfans.main.schedule

import io.github.miun173.footballfans.repository.remote.MatchRepo
import io.github.miun173.footballfans.utils.DateTime
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class SchedulePresenter(private val schedulerView: ScheduleContract.SchedulerView,
                        private val matchRepo: MatchRepo)
    : ScheduleContract.Presenter {

    override fun getEvents(id: String?, isNext: Boolean) {
        schedulerView.showLoading(true)

        doAsync {
            val res = when(isNext) {
                true -> id?.toInt()?.let { matchRepo.getNext15Events(it) } ?: emptyList()
                false -> id?.toInt()?.let { matchRepo.getLast15Events(it) } ?: emptyList()
            }

            res.map {
                it.dateEvent = it.dateEvent?.let { it1 -> DateTime.getShortDate(it1) }
            }

            uiThread {
                schedulerView.showLoading(false)
                println("events:" + res.toString())

                schedulerView.showEvents(res)
            }
        }
    }
}
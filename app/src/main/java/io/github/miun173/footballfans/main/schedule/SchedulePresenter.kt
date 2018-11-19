package io.github.miun173.footballfans.main.schedule

import io.github.miun173.footballfans.repository.remote.MatchRepo
import io.github.miun173.footballfans.utils.DateTime
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class SchedulePresenter(private val view: ScheduleContract.SchedulerView,
                        private val matchRepo: MatchRepo)
    : ScheduleContract.Presenter {

    override fun getEvents(id: Int, isNext: Boolean) {
        view.showLoading(true)

        doAsync {
            val res = when(isNext) {
                true -> matchRepo.getNext15Events(id)
                false -> matchRepo.getLast15Events(id)
            }

            println("res >>> $res")

            uiThread {
                view.showLoading(false)
                println("events:" + res.toString())
                view.showEvents(res)
            }
        }
    }

    override fun getLeagues() {
        doAsync {
            val leagues = matchRepo.getLeagues()
            uiThread {
                if(!leagues.isEmpty()) {
                    view.setSpinner(leagues)
                }
            }
        }
    }
}
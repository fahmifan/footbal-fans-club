package io.github.miun173.footballfans.main.schedule

import com.google.gson.Gson
import io.github.miun173.footballfans.model.Events
import io.github.miun173.footballfans.repository.remote.Fetch
import io.github.miun173.footballfans.repository.remote.TheSportDbRoute
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.util.*

class SchedulePresenter(private val schedulerView: ScheduleContract.SchedulerView,
                        private val fetch: Fetch,
                        private val gson: Gson)
    : ScheduleContract.Presenter {

    private fun convertDate(date: String): Calendar {
        val dateChar = date.split("-")
        val newDate = GregorianCalendar()
        newDate.set(dateChar[0].toInt(), dateChar[1].toInt(), dateChar[2].toInt())
        return newDate
    }

    override fun getEvents(id: String?, isNext: Boolean) {
        schedulerView.showLoading(true)

        var res: Events
        doAsync {
            res = gson.fromJson(
                    fetch.doReq(TheSportDbRoute.getLast15Events(id)),
                    Events::class.java)

            if(isNext) {
                res = gson.fromJson(
                        fetch.doReq(TheSportDbRoute.getNext15Events(id)),
                        Events::class.java)
            }

            res.events?.map {
                val date = it.dateEvent?.let { it1 -> convertDate(it1) }
                it.dateEvent = "${date?.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault())}, " +
                        "${date?.get(Calendar.DATE)} " +
                        "${date?.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault())} " +
                        "${date?.get(java.util.Calendar.YEAR)}"
            }

            uiThread {
                schedulerView.showLoading(false)
                println("events:" + res.events.toString())

                res.events?.let { its -> schedulerView.showEvents(its) }
            }
        }
    }
}
package io.github.miun173.footballfans.main

import com.google.gson.Gson
import io.github.miun173.footballfans.model.Events
import io.github.miun173.footballfans.repository.Fetch
import io.github.miun173.footballfans.repository.TheSportDbRoute
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.util.*

class MainPresenter(private val view: MainContract.View,
                    private val fetch: Fetch,
                    private val gson: Gson)
    :  MainContract.Presenter {

    private fun convertDate(date: String): Calendar {
        val dateChar = date.split("-")
        val newDate = GregorianCalendar()
        newDate.set(dateChar[0].toInt(), dateChar[1].toInt(), dateChar[2].toInt())
        return newDate
    }

    override fun getNext15Events(id: String?) {
        view.showLoading()

        doAsync {
            val res = gson.fromJson(
                    fetch.doReq(TheSportDbRoute.getNext15Events(id)),
                    Events::class.java)

            res.events?.map {
                val date = it.dateEvent?.let { it1 -> convertDate(it1) }
                it.dateEvent = "${date?.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault())}, ${date?.get(Calendar.DATE)} " +
                        "${date?.get(java.util.Calendar.YEAR)}"
            }

            uiThread {
                view.hideLoading()
                println("events:" + res.events.toString())

                // i don't know what this is do
                res.events?.let { its -> view.showEvents(its) }
            }
        }
    }

    override fun getLast15Events(id: String?) {
        view.showLoading()

        doAsync {
            val res = gson.fromJson(
                    fetch.doReq(TheSportDbRoute.getLast15Events(id)),
                    Events::class.java)

            res.events?.map {
                val date = it.dateEvent?.let { it1 -> convertDate(it1) }
                it.dateEvent = "${date?.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault())}, ${date?.get(Calendar.DATE)} " +
                        "${date?.get(java.util.Calendar.YEAR)}"
            }

            uiThread {
                view.hideLoading()
                println("events:" + res.events.toString())

                // i don't know what this is do
                res.events?.let { its -> view.showEvents(its) }
            }
        }
    }
}
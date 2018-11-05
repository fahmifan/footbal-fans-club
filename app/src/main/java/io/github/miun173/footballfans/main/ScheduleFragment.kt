package io.github.miun173.footballfans.main

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.gson.Gson
import io.github.miun173.footballfans.R
import io.github.miun173.footballfans.detail.DetailActivity
import io.github.miun173.footballfans.model.Event
import io.github.miun173.footballfans.repository.remote.FetchImpl
import kotlinx.android.synthetic.main.fragment_schedule.*

class ScheduleFragment: Fragment(), ScheduleContract.SchedulerView {
    private lateinit var rvAdapterMain: ScheduleRVAdapter
    private lateinit var rv: RecyclerView
    private lateinit var rvManager: RecyclerView.LayoutManager

    lateinit var presenter: SchedulePresenter

    val EVENT_ID = "4328"
    var isNext = false

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_schedule, parent, false)

        presenter = SchedulePresenter(this, FetchImpl(), Gson())
//        presenter.getEvents(EVENT_ID, isNext)

        return view
    }

    override fun onStart() {
        super.onStart()
        presenter.getEvents(EVENT_ID, isNext)
    }


    override fun showEvents(events: List<Event>) {
        rvManager = LinearLayoutManager(context)
        rvAdapterMain = ScheduleRVAdapter(events) {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("event", it)
            intent.putExtra("event_id", it.idEvent?.toInt())
            startActivity(intent)
        }

        rv = events_list.apply {
            setHasFixedSize(true)
            layoutManager = rvManager
            adapter = rvAdapterMain
        }
    }

    override fun showLoading(show: Boolean) {
        if(show) {
            Toast.makeText(context, "Loading...", Toast.LENGTH_SHORT).show()
        }
    }
}
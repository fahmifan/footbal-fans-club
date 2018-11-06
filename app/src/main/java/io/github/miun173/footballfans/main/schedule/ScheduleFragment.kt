package io.github.miun173.footballfans.main.schedule

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
import io.github.miun173.footballfans.main.EventsRVAdapter
import io.github.miun173.footballfans.model.Event
import io.github.miun173.footballfans.repository.remote.FetchImpl
import kotlinx.android.synthetic.main.fragment_schedule.*

class ScheduleFragment: Fragment(), ScheduleContract.SchedulerView {
    private lateinit var rvAdapterMain: EventsRVAdapter
    private lateinit var rvManager: RecyclerView.LayoutManager

    lateinit var presenter: SchedulePresenter

    val EVENT_ID = "4328"
    var isNext = false

    val events: MutableList<Event> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_schedule, parent, false)

        presenter = SchedulePresenter(this, FetchImpl(), Gson())
        rvManager = LinearLayoutManager(context)

        rvAdapterMain = EventsRVAdapter(events as MutableList<Event>) {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("event", it)
            intent.putExtra("event_id", it.idEvent?.toInt())
            startActivity(intent)
        }

        presenter.getEvents(EVENT_ID, isNext)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        events_list.apply {
            layoutManager = rvManager
            adapter = rvAdapterMain
        }
    }

    override fun showEvents(events: List<Event>) {
        this.events.clear()
        this.events.addAll(events)

//        events_list.apply {
//            layoutManager = rvManager
//            adapter = rvAdapterMain
//        }

        rvAdapterMain.notifyDataSetChanged()
    }

    override fun showLoading(show: Boolean) {
        if(show) {
            Toast.makeText(context, "Loading...", Toast.LENGTH_SHORT).show()
        }
    }
}
package io.github.miun173.footballfans.main.schedule

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.miun173.footballfans.R
import io.github.miun173.footballfans.detail.DetailActivity
import io.github.miun173.footballfans.main.EventsRVAdapter
import io.github.miun173.footballfans.model.Event
import io.github.miun173.footballfans.model.League
import io.github.miun173.footballfans.repository.remote.MatchRepoImpl
import kotlinx.android.synthetic.main.fragment_schedule.*

class ScheduleFragment: Fragment(), ScheduleContract.SchedulerView, AdapterView.OnItemSelectedListener {
    private lateinit var rvAdapterMain: EventsRVAdapter
    private lateinit var rvManager: RecyclerView.LayoutManager
    private lateinit var presenter: SchedulePresenter
    private val EVENT_ID = "4328"
    private val events: MutableList<Event> = mutableListOf()
    // this will be modified from outer class
    var isNext = false

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_schedule, parent, false)

        presenter = SchedulePresenter(this, MatchRepoImpl())
        rvManager = LinearLayoutManager(context)

        rvAdapterMain = EventsRVAdapter(events) {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(getString(R.string.intent_event), it)
            intent.putExtra(getString(R.string.intent_event_id), it.idEvent?.toInt())
            startActivity(intent)
        }

        presenter.getEvents(EVENT_ID.toInt(), isNext)

        return view
    }

    override fun setSpinner(leagues: List<League>) {
        val aa = ArrayAdapter(context, android.R.layout.simple_spinner_item, leagues)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        match_spinner.apply {
            adapter = aa
            setSelection(0, false)
            onItemSelectedListener = this@ScheduleFragment
            prompt = "Select A League"
            gravity = Gravity.CENTER
        }

        val NEW_SPINNER_ID = 1
        Spinner(context).id = NEW_SPINNER_ID
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
        Toast.makeText(context, "Selected: $pos", Toast.LENGTH_SHORT).show()
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        match_spinner.onItemSelectedListener = this

        events_list.apply {
            layoutManager = rvManager
            adapter = rvAdapterMain
        }
    }

    override fun showEvents(events: List<Event>) {
        this.events.clear()
        this.events.addAll(events)
        rvAdapterMain.notifyDataSetChanged()
    }

    override fun showLoading(show: Boolean) {
        if(show) {
            Toast.makeText(context, "Loading...", Toast.LENGTH_SHORT).show()
        }
    }
}
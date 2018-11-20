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
import io.github.miun173.footballfans.matchdetail.MatchDetailActivity
import io.github.miun173.footballfans.main.EventsRVAdapter
import io.github.miun173.footballfans.model.Event
import io.github.miun173.footballfans.model.League
import io.github.miun173.footballfans.repository.remote.MatchRemoteImpl
import kotlinx.android.synthetic.main.fragment_schedule.*

class ScheduleFragment: Fragment(), ScheduleContract.SchedulerView, AdapterView.OnItemSelectedListener {
    private lateinit var rvAdapterMain: EventsRVAdapter
    private lateinit var rvManager: RecyclerView.LayoutManager
    private lateinit var presenter: SchedulePresenter
    private val EVENT_ID = "4328"
    private val events: MutableList<Event> = mutableListOf()
    lateinit var arrayAdapter: ArrayAdapter<League>
    private val leagues: MutableList<League> = mutableListOf()

    // this will be modified from outer class
    var isNext = false

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_schedule, parent, false)

        rvManager = LinearLayoutManager(context)

        rvAdapterMain = EventsRVAdapter(events) {
            val intent = Intent(context, MatchDetailActivity::class.java)
            intent.putExtra(getString(R.string.intent_event), it)
            intent.putExtra(getString(R.string.intent_event_id), it.idEvent?.toInt())
            startActivity(intent)
        }

        arrayAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, leagues)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        presenter = SchedulePresenter(this, MatchRemoteImpl())
        presenter.getEvents(EVENT_ID.toInt(), isNext)
        presenter.getLeagues()

        return view
    }

    override fun setSpinner(leagues: List<League>) {
        this.leagues.clear()
        this.leagues.addAll(leagues)
        arrayAdapter.notifyDataSetChanged()
        val NEW_SPINNER_ID = 1
        Spinner(context).id = NEW_SPINNER_ID
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
        presenter.getEvents(leagues[pos].idLeague ?: 0, isNext)
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        match_spinner.apply {
            adapter = arrayAdapter
            setSelection(0, false)
            onItemSelectedListener = this@ScheduleFragment
            prompt = "Select A League"
            gravity = Gravity.CENTER
        }

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
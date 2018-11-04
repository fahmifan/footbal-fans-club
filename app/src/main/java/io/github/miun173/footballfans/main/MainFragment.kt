package io.github.miun173.footballfans.main

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import io.github.miun173.footballfans.R
import io.github.miun173.footballfans.detail.DetailActivity
import io.github.miun173.footballfans.model.Event
import io.github.miun173.footballfans.model.Team
import io.github.miun173.footballfans.repository.FetchImpl
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment: Fragment(), MainContract.View {
    lateinit var rvAdapterMain: MainRecyclerViewAdapter
    lateinit var rv: RecyclerView
    lateinit var rvManager: RecyclerView.LayoutManager

    val EVENT_ID = "4328"
    var isNext = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val presenter = MainPresenter(this, FetchImpl(), Gson())
        presenter.getEvents(EVENT_ID, isNext)

        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun showEvents(events: List<Event>) {
        rvManager = LinearLayoutManager(context)
        rvAdapterMain = MainRecyclerViewAdapter(events) {
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

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showTeamList(teams: List<Team>?) {
    }


}
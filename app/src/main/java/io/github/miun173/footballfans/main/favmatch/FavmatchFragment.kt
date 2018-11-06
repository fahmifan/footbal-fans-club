package io.github.miun173.footballfans.main.favmatch

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import io.github.miun173.footballfans.R
import io.github.miun173.footballfans.detail.DetailActivity
import io.github.miun173.footballfans.main.EventsRVAdapter
import io.github.miun173.footballfans.model.Event
import io.github.miun173.footballfans.repository.local.DBManagerImpl
import io.github.miun173.footballfans.repository.remote.FetchImpl
import kotlinx.android.synthetic.main.fragment_schedule.*

class FavmatchFragment: Fragment(), FavmatchContract.View {
    lateinit var presenter: FavmatchContract.Presenter
    private lateinit var rvEventAdapter: EventsRVAdapter
    private lateinit var rv: RecyclerView
    private lateinit var rvManager: RecyclerView.LayoutManager

    val events: MutableList<Event> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_favmatch, parent, false)

        presenter = FavmatchPresenter(this, FetchImpl(), DBManagerImpl(context!!))
        presenter.getFavmatch()

        rvManager = LinearLayoutManager(context)
        rvEventAdapter = EventsRVAdapter(events as MutableList<Event>) {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("event", it)
            intent.putExtra("event_id", it.idEvent?.toInt())
            startActivity(intent)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv = events_list.apply {
            setHasFixedSize(true)
            layoutManager = rvManager
            adapter = rvEventAdapter
        }
    }

    override fun setFavmatch(events: List<Event?>) {
        // set list
        this.events.clear()
        this.events.addAll(events as MutableList<Event>)
        rvEventAdapter.notifyDataSetChanged()
    }

    override fun showLoading(show: Boolean) {
        if(show) {
            Toast.makeText(context, "Loading...", Toast.LENGTH_SHORT).show()
        }
    }

    override fun showNoFav() {
        Toast.makeText(context, "No favorite match...", Toast.LENGTH_SHORT).show()
    }
}
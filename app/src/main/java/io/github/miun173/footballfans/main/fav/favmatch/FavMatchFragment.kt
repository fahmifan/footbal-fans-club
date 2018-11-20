package io.github.miun173.footballfans.main.fav.favmatch

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.miun173.footballfans.R
import io.github.miun173.footballfans.R.id.favmatch_layout_swiped
import io.github.miun173.footballfans.matchdetail.MatchDetailActivity
import io.github.miun173.footballfans.main.EventsRVAdapter
import io.github.miun173.footballfans.model.Event
import io.github.miun173.footballfans.repository.local.MatchLocalImpl
import io.github.miun173.footballfans.repository.remote.MatchRemoteImpl
import kotlinx.android.synthetic.main.fragment_favmatch.*

class FavMatchFragment: Fragment(), FavMatchContract.View {
    private lateinit var presenter: FavMatchContract.Presenter
    private lateinit var rvEventAdapter: EventsRVAdapter
    private lateinit var rv: RecyclerView
    private lateinit var rvManager: RecyclerView.LayoutManager

    private val events: MutableList<Event> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_favmatch, parent, false)

        // if there is no context there is no way to access the DB, so just NPE it
        presenter = FavMatchPresenter(this, MatchRemoteImpl(), MatchLocalImpl(context!!))
        presenter.getFavmatch()

        rvManager = LinearLayoutManager(context)
        rvEventAdapter = EventsRVAdapter(events) {
            val intent = Intent(context, MatchDetailActivity::class.java)
            intent.putExtra("event", it)
            intent.putExtra("event_id", it.idEvent?.toInt())
            startActivity(intent)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv = rv_favmatch.apply {
            setHasFixedSize(true)
            layoutManager = rvManager
            adapter = rvEventAdapter
        }


        favmatch_layout_swiped.setOnRefreshListener {
            favmatch_layout_swiped.isRefreshing = true
            presenter.getFavmatch()
        }
    }

    override fun setFavmatch(event: List<Event?>) {
        // set list
        this.events.clear()
        this.events.addAll(event as List<Event>)
        rvEventAdapter.notifyDataSetChanged()
        favmatch_layout_swiped?.isRefreshing = false
    }

    override fun showLoading(show: Boolean) {
        favmatch_layout_swiped?.isRefreshing = show

    }

    override fun showNoFav() {
        Toast.makeText(context, "No Favorite Match", Toast.LENGTH_SHORT).show()
        favmatch_layout_swiped?.isRefreshing = false
    }

    override fun showFavFailed() {
        Toast.makeText(context, "Failed load favorite match", Toast.LENGTH_SHORT).show()
        favmatch_layout_swiped?.isRefreshing = false
    }
}
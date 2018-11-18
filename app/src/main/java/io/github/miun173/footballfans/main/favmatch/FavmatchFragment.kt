package io.github.miun173.footballfans.main.favmatch

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.miun173.footballfans.R
import io.github.miun173.footballfans.detail.DetailActivity
import io.github.miun173.footballfans.main.EventsRVAdapter
import io.github.miun173.footballfans.model.Event
import io.github.miun173.footballfans.repository.local.DBManagerImpl
import io.github.miun173.footballfans.repository.remote.MatchRepoImpl
import kotlinx.android.synthetic.main.fragment_favmatch.*

class FavmatchFragment: Fragment(), FavmatchContract.View {
    private lateinit var presenter: FavmatchContract.Presenter
    private lateinit var rvEventAdapter: EventsRVAdapter
    private lateinit var rv: RecyclerView
    private lateinit var rvManager: RecyclerView.LayoutManager
    private lateinit var searchView: SearchView

    private val events: MutableList<Event> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_favmatch, parent, false)

        // if there is no context there is no way to access the DB, so just NPE it
        presenter = FavmatchPresenter(this, MatchRepoImpl(), DBManagerImpl(context!!))
        presenter.getFavmatch()

        rvManager = LinearLayoutManager(context)
        rvEventAdapter = EventsRVAdapter(events) {
            val intent = Intent(context, DetailActivity::class.java)
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

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.appbar_menu, menu)

        val searchItem = menu?.findItem(R.id.action_search)
        searchView = searchItem?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(context, query, Toast.LENGTH_SHORT).show()
                // do search
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
    }

    override fun setFavmatch(event: List<Event?>) {
        // set list
        this.events.clear()
        this.events.addAll(event as List<Event>)
        rvEventAdapter.notifyDataSetChanged()
        favmatch_layout_swiped.isRefreshing = false
    }

    override fun showLoading(show: Boolean) {
        if(show) {
            Toast.makeText(context, "Loading...", Toast.LENGTH_SHORT).show()
        }
    }

    override fun showNoFav() {
        Toast.makeText(context, "No Favorite Match", Toast.LENGTH_SHORT).show()
    }

    override fun showFavFailed() {
        Toast.makeText(context, "Failed load favorite match", Toast.LENGTH_SHORT).show()
    }

}
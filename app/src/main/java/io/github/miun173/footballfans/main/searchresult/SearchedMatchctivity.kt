package io.github.miun173.footballfans.main.searchresult

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.miun173.footballfans.R
import io.github.miun173.footballfans.matchdetail.MatchDetailActivity
import io.github.miun173.footballfans.main.EventsRVAdapter
import io.github.miun173.footballfans.model.Event
import io.github.miun173.footballfans.repository.remote.MatchRemoteImpl
import kotlinx.android.synthetic.main.activity_searched_match.*

class SearchedMatchctivity : AppCompatActivity(), SearchedMatchContract.View {
    private val events: MutableList<Event> = mutableListOf()

    lateinit var rvManager: RecyclerView.LayoutManager
    lateinit var matchAdapter: EventsRVAdapter
    lateinit var presenter: SearchedMatchContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_searched_match)

        presenter = SearchMatchPresenter(this, MatchRemoteImpl())

        // set recyclerview
        rvManager = LinearLayoutManager(applicationContext)
        matchAdapter = EventsRVAdapter(events) {
            val intent = Intent(applicationContext, MatchDetailActivity::class.java)
            intent.putExtra(getString(R.string.intent_event), it)
            intent.putExtra(getString(R.string.intent_event_id), it.idEvent?.toInt())
            startActivity(intent)
        }

        val query = intent.getStringExtra(getString(R.string.query)) ?: ""
        presenter.search(query)

        searched_match_list.apply {
            layoutManager = rvManager
            adapter = matchAdapter
        }
    }



    override fun setResult(events: List<Event>) {
        Toast.makeText(this, "Found", Toast.LENGTH_SHORT).show()
        this.events.clear()
        this.events.addAll(events)
        matchAdapter.notifyDataSetChanged()
    }

    override fun setEmptyResult(show: Boolean) {
        if(show) {
            Toast.makeText(this, "event not found", Toast.LENGTH_SHORT).show()
        }
    }

    override fun setLoading(show: Boolean) {
        if(show) {
            Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show()
        }
    }
}

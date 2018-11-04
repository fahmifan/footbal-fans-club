package io.github.miun173.footballfans.main

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.google.gson.Gson
import io.github.miun173.footballfans.R
import io.github.miun173.footballfans.detail.DetailActivity
import io.github.miun173.footballfans.model.Event
import io.github.miun173.footballfans.model.Team
import io.github.miun173.footballfans.repository.FetchImpl
import kotlinx.android.synthetic.main.activity_main.*

//@ContainerOptions(cache = CacheImplementation.SPARSE_ARRAY)
class MainActivity : AppCompatActivity(), MainContract.View {
    lateinit var rvAdapter: RecyclerViewAdapter
    lateinit var rv: RecyclerView
    lateinit var rvManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val presenter = MainPresenter(this, FetchImpl(), Gson())
        presenter.getEvents("4328")
    }

    override fun showEvents(events: List<Event>) {
        rvManager = LinearLayoutManager(this)
        rvAdapter = RecyclerViewAdapter(events) {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("event", it)

            startActivity(intent)
        }

        rv = events_list.apply {
            setHasFixedSize(true)
            layoutManager = rvManager
            adapter = rvAdapter
        }
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showTeamList(teams: List<Team>?) {
        println(teams.toString())
    }
}

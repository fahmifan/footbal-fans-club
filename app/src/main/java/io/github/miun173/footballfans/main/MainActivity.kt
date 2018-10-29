package io.github.miun173.footballfans.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.google.gson.Gson
import io.github.miun173.footballfans.Item
import io.github.miun173.footballfans.R
import io.github.miun173.footballfans.model.Event
import io.github.miun173.footballfans.model.Team
import io.github.miun173.footballfans.repository.ApiRepoImpl
import kotlinx.android.extensions.CacheImplementation
import kotlinx.android.extensions.ContainerOptions
import kotlinx.android.synthetic.main.activity_main.*

@ContainerOptions(cache = CacheImplementation.SPARSE_ARRAY)
class MainActivity : AppCompatActivity(), MainContract.View {
    lateinit var rvAdapter: RecyclerViewAdapter
    lateinit var rv: RecyclerView
    lateinit var rvManager: RecyclerView.LayoutManager

    private var items: MutableList<Item> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val presenter = MainPresenter(this, ApiRepoImpl(), Gson())
//        presenter.getTeamsLeague("English Premier League")
        presenter.getEvents("4328")
    }

    override fun showEvents(events: List<Event>) {
        rvManager = LinearLayoutManager(this)
        rvAdapter = RecyclerViewAdapter(events) {
            Toast.makeText(this, it.teamName, Toast.LENGTH_SHORT).show()
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

    private fun initData() {
        val name = resources.getStringArray(R.array.club_name)
        val image = resources.obtainTypedArray(R.array.club_image)
        val details = resources.getStringArray(R.array.club_detail)

        items.clear()

        for (i in name.indices) {
            items.add(Item(name[i], image.getResourceId(i, 0), details[i]))
        }

        // Recycle the typed array
        image.recycle()
    }
}

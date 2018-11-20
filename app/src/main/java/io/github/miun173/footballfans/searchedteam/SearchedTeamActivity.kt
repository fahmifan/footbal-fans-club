package io.github.miun173.footballfans.searchedteam

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.miun173.footballfans.R
import io.github.miun173.footballfans.main.team.TeamRVAdapter
import io.github.miun173.footballfans.model.Team
import io.github.miun173.footballfans.repository.remote.MatchRemoteImpl
import io.github.miun173.footballfans.teamdetail.TeamDetailActivity
import kotlinx.android.synthetic.main.activity_team_search.*

class SearchedTeamActivity : AppCompatActivity(), SearchedTeamContract.View {
    lateinit var rvManager: RecyclerView.LayoutManager
    lateinit var rvAdapter: TeamRVAdapter
    lateinit var presenter: SearchedTeamContract.Presenter

    private val teams: MutableList<Team> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_search)

        rvManager = LinearLayoutManager(applicationContext)
        rvAdapter = TeamRVAdapter(teams) {
            val intent = Intent(applicationContext, TeamDetailActivity::class.java)
            intent.putExtra(getString(R.string.intent_team), it)
            startActivity(intent)
        }

        val query = intent.getStringExtra(getString(R.string.query)) ?: ""

        list_team.let {
            it.adapter = rvAdapter
            it.layoutManager = rvManager
        }

        presenter = SearchedTeamPresenter(this, MatchRemoteImpl())
        presenter.search(query)
    }

    override fun setResult(teams: List<Team>) {
        this.teams.clear()
        this.teams.addAll(teams)
        rvAdapter.notifyDataSetChanged()
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

package io.github.miun173.footballfans.main.fav.favteam

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.miun173.footballfans.R
import io.github.miun173.footballfans.main.team.TeamRVAdapter
import io.github.miun173.footballfans.model.Team
import io.github.miun173.footballfans.repository.local.TeamLocalImpl
import io.github.miun173.footballfans.repository.remote.MatchRemoteImpl
import io.github.miun173.footballfans.teamdetail.TeamDetailActivity
import kotlinx.android.synthetic.main.fragment_favmatch.*

class FavTeamFragment: Fragment(), FavTeamContract.View {
    lateinit var rvManager: RecyclerView.LayoutManager
    lateinit var rvAdapter: TeamRVAdapter
    lateinit var presenter: FavTeamContract.Presenter
    val teams: MutableList<Team> = mutableListOf()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rvManager = LinearLayoutManager(context)
        rvAdapter = TeamRVAdapter(teams) {
            val intent = Intent(context, TeamDetailActivity::class.java)
            intent.putExtra(getString(R.string.intent_team), it)
            startActivity(intent)
        }

        // if it can't access the DB just crashed it
        presenter = FavTeamPresenter(this, MatchRemoteImpl(), TeamLocalImpl(context!!))
        presenter.getFavmatch()

        return inflater.inflate(R.layout.fragment_favmatch, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_favmatch.let {
            it.layoutManager = rvManager
            it.adapter = rvAdapter
        }

        favmatch_layout_swiped.setOnRefreshListener {
            favmatch_layout_swiped?.isRefreshing = true
            presenter.getFavmatch()
        }
    }


    override fun setFavmatch(teams: List<Team?>) {
        Toast.makeText(context, "Found favorite teams", Toast.LENGTH_SHORT).show()
        this.teams.clear()
        this.teams.addAll(teams as MutableList<Team>)
        rvAdapter.notifyDataSetChanged()
        favmatch_layout_swiped?.isRefreshing = false
    }

    override fun showLoading(show: Boolean) {
        favmatch_layout_swiped?.isRefreshing = show
    }

    override fun showNoFav() {
        Toast.makeText(context, "No favorite team", Toast.LENGTH_SHORT).show()
        favmatch_layout_swiped?.isRefreshing = false
    }

    override fun showFavFailed() {
        Toast.makeText(context, "Failed to load teams", Toast.LENGTH_SHORT).show()
        favmatch_layout_swiped?.isRefreshing = false
    }
}
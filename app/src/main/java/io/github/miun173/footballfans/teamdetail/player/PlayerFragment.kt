package io.github.miun173.footballfans.teamdetail.player

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.miun173.footballfans.PlayerDetailActivity
import io.github.miun173.footballfans.R
import io.github.miun173.footballfans.model.Player
import io.github.miun173.footballfans.model.Team
import io.github.miun173.footballfans.repository.remote.MatchRepoImpl
import io.github.miun173.footballfans.teamdetail.PlayerRVAdapter
import kotlinx.android.synthetic.main.fragment_team_player.*

class PlayerFragment: Fragment(), PlayerContract.View {
    lateinit var playerAdapter: PlayerRVAdapter
    lateinit var rvManager: RecyclerView.LayoutManager
    lateinit var presenter: PlayerContract.Presenter

    private val players: MutableList<Player> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_team_player, container, false)

        rvManager = LinearLayoutManager(context)
        playerAdapter = PlayerRVAdapter(players) {
            // Go to player details
            val intent = Intent(context, PlayerDetailActivity::class.java)
            intent.putExtra(getString(R.string.intent_player), it)
            startActivity(intent)
        }

        presenter = PlayerPresenter(this, MatchRepoImpl())

        val team = arguments?.get(getString(R.string.intent_team)) as Team
        presenter.getTeamPlayers(team.teamName ?: "")

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        rv_team_player.layoutManager = rvManager
        rv_team_player.adapter = playerAdapter
    }

    override fun setLoading(show: Boolean) {
        Toast.makeText(context, "Loading players...", Toast.LENGTH_SHORT).show()
    }

    override fun setPlayers(players: List<Player>) {
        this.players.clear()
        this.players.addAll(players)
        playerAdapter.notifyDataSetChanged()
    }

    override fun setEmptyPlayers(show: Boolean) {
        if(show) {
            Toast.makeText(context, "Players not found", Toast.LENGTH_SHORT).show()
        }
    }
}
package io.github.miun173.footballfans.main.team

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.miun173.footballfans.R
import io.github.miun173.footballfans.model.League
import io.github.miun173.footballfans.model.Team
import io.github.miun173.footballfans.repository.remote.MatchRepoImpl
import kotlinx.android.synthetic.main.fragment_team.*

class TeamFragment: Fragment(), TeamContract.View, AdapterView.OnItemSelectedListener {
    lateinit var rvAdapter: TeamRVAdapter
    lateinit var rvManager: RecyclerView.LayoutManager
    lateinit var presenter: TeamContract.Presenter
    lateinit var arrayAdapter: ArrayAdapter<League>

    private val EVENT_ID = 4328

    private val teams: MutableList<Team> = mutableListOf()
    private val leagues: MutableList<League> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_team, container, false)

        // init view
        rvManager = LinearLayoutManager(context)
        rvAdapter = TeamRVAdapter(teams) {
            // go to teams detail
            Toast.makeText(context, "Go to team detail", Toast.LENGTH_SHORT).show()
        }

        arrayAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, leagues)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        presenter = TeamPresenter(this, MatchRepoImpl())
        presenter.getLeagues()
        presenter.getTeamsLeague(EVENT_ID)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        team_list.apply {team_list
            layoutManager = rvManager
            adapter = rvAdapter
        }

        spinner_team.apply {
            adapter = arrayAdapter
            onItemSelectedListener = this@TeamFragment
        }

        val NEW_SPINNER_ID = 1
        (Spinner(context)).id = NEW_SPINNER_ID
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        presenter.getTeamsLeague(leagues[position].idLeague ?: 0)
    }

    override fun setSpinner(leagues: List<League>) {
        this.leagues.clear()
        this.leagues.addAll(leagues)
        arrayAdapter.notifyDataSetChanged()
    }

    override fun showLoading(show: Boolean) {
        Toast.makeText(context, "Loading...", Toast.LENGTH_SHORT).show()
    }

    override fun setListTeam(teams: List<Team>) {
        this.teams.clear()
        this.teams.addAll(teams)

        println("team fragment >> $teams")

        rvAdapter.notifyDataSetChanged()
    }

    override fun setTeamEmpty(show: Boolean) {
        Toast.makeText(context, "Team not found", Toast.LENGTH_SHORT).show()
    }
}
package io.github.miun173.footballfans.teamdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import io.github.miun173.footballfans.R
import io.github.miun173.footballfans.model.Team
import kotlinx.android.synthetic.main.fragment_team_overview.*

class TeamOverview: Fragment() {
    lateinit var team: Team

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        team = arguments?.get(getString(R.string.intent_team)) as Team
        return inflater.inflate(R.layout.fragment_team_overview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Picasso.get()
                .load(team.teamBadge)
                .placeholder(R.drawable.img_load)
                .error(R.drawable.img_error)
                .into(team_logo)

        team_description.text = team.strDescriptionEN
    }
}
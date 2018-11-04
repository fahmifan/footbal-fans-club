package io.github.miun173.footballfans.detail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.squareup.picasso.Picasso
import io.github.miun173.footballfans.R
import io.github.miun173.footballfans.model.Event
import io.github.miun173.footballfans.repository.FetchImpl
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity(), DetailContract.View {
    lateinit var event: Event
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        event = intent.getParcelableExtra("event")
        val eventID = intent.getIntExtra("event_id", 0)

        val presenter = DetailPresenter(this, FetchImpl())

        presenter.getEventDetail(eventID)
        presenter.getTeam(event.strHomeTeam, event.strAwayTeam)
    }

    override fun setLogo(homeBadge: String?, awayBadge: String?) {
        Picasso.get().load(homeBadge)
                .placeholder(R.drawable.img_load)
                .error(R.drawable.img_error)
                .into(iv_home)
        Picasso.get().load(awayBadge)
                .placeholder(R.drawable.img_load)
                .error(R.drawable.img_error)
                .into(iv_away)
    }

    override fun setEventDetail(event: Event?) {
        tv_home_team.text = event?.strHomeTeam ?: ""
        tv_away_team.text = event?.strAwayTeam ?: ""
        tv_date.text = event?.dateEvent ?: ""

        tv_shots_home.text = event?.intHomeShots ?: ""
        tv_shots_away.text = event?.intAwayShots ?: ""

        val score = "${event?.intHomeScore ?: " "} vs ${event?.intAwayScore ?: " "}"
        tv_score.text = score

        goal_home.text = event?.strHomeGoalDetails ?: ""
        goal_away.text = event?.strAwayGoalDetails ?: ""

        gk_home.text = event?.strHomeLineupGoalkeeper ?: ""
        gk_away.text = event?.strAwayLineupGoalkeeper ?: ""

        def_away.text = event?.strAwayLineupDefense ?: ""
        def_home.text = event?.strHomeLineupDefense ?: ""

        mid_away.text = event?.strAwayLineupMidfield ?: ""
        mid_home.text = event?.strHomeLineupMidfield ?: ""

        fw_home.text = event?.strHomeLineupForward ?: ""
        fw_away.text = event?.strAwayLineupForward ?: ""
    }
}

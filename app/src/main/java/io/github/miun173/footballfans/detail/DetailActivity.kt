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

        val presenter = DetailPresenter(this, FetchImpl())
        presenter.getTeam(event.strHomeTeam, event.strAwayTeam)
    }

    override fun setLogo(homeBadge: String?, awayBadge: String?) {
        Picasso.get().load(homeBadge).into(iv_home)
        Picasso.get().load(awayBadge).into(iv_away)
    }

    override fun setHeader() {
        tv_home_team.text = event.strHomeTeam
        tv_away_team.text = event.strAwayTeam
        tv_date.text = event.dateEvent
        tv_score.text = "${event.intHomeScore} vs ${event.intAwayScore}"
    }

    fun splitEnterCutString(str: String?, delimiters: String): String {
        val seq = str?.split(delimiters)
        var newString = ""
        for(i in 0..((seq?.size)?.minus(1) ?:  0 ?: 0)) {
            newString += (seq?.get(i)?: "") + "\n"
        }

        return newString
    }

    override fun setShot() {
        tv_shots_home.text = splitEnterCutString(event.strHomeGoalDetails, ";")
        tv_shots_away.text = splitEnterCutString(event.strAwayGoalDetails, ";")
    }


}

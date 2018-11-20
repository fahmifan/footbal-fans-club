package io.github.miun173.footballfans.matchdetail

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.squareup.picasso.Picasso
import io.github.miun173.footballfans.R
import io.github.miun173.footballfans.model.Event
import io.github.miun173.footballfans.repository.local.MatchLocalImpl
import io.github.miun173.footballfans.repository.remote.MatchRemote
import io.github.miun173.footballfans.repository.remote.MatchRemoteImpl
import kotlinx.android.synthetic.main.activity_detail.*

class MatchDetailActivity : AppCompatActivity(), MatchDetailContract.View {
    private lateinit var event: Event
    private lateinit var presenterMatch: MatchDetailPresenter
    private var eventID: Int = 0
    private var menuItem: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        setSupportActionBar(detail_toolbar)
        println("action bar showed >>> " + supportActionBar?.isShowing)

        event = intent.getParcelableExtra("event")
        eventID = event.idEvent?.toInt() ?: -1

        presenterMatch = MatchDetailPresenter(this, MatchRemoteImpl(),
                MatchLocalImpl(applicationContext))

        presenterMatch.getEventDetail(eventID)
        presenterMatch.getTeam(event.strHomeTeam, event.strAwayTeam)

        swipe.setOnRefreshListener {
            swipe?.isRefreshing = true
            presenterMatch.getEventDetail(eventID)
            presenterMatch.getTeam(event.strHomeTeam, event.strAwayTeam)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.fav_menu, menu)
        menuItem = menu

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when(item?.itemId) {
        R.id.menu_add_fav -> {
            presenterMatch.setFaved(event)
            true
        }

        else -> {
            false
        }
    }

    override fun showFav(show: Boolean) {
        if(show) {
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this,
                R.drawable.ic_star_white_24dp)
            return
        }

        menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this,
                R.drawable.ic_star_border_white_24dp)
    }

    override fun showSetFavSuccess() {
        Toast.makeText(applicationContext, "Match added to favorite", Toast.LENGTH_SHORT).show()
    }

    override fun showSetFavFailed() {
        Toast.makeText(applicationContext, "Unable add to favorite", Toast.LENGTH_SHORT).show()
    }

    override fun showUnsetSuccess() {
        Toast.makeText(applicationContext, "Match removed from favorite", Toast.LENGTH_SHORT).show()
    }

    override fun showEmptyEvent(show: Boolean) {
        Toast.makeText(applicationContext, "No match", Toast.LENGTH_SHORT).show()
    }

    override fun showUnsetFavFailed() {
        Toast.makeText(applicationContext, "Failed to removed from favorite", Toast.LENGTH_SHORT).show()
    }

    override fun setLogo(homeBadge: String?, awayBadge: String?) {
        Picasso.get().load(homeBadge)
                .placeholder(R.drawable.img_placholder)
                .error(R.drawable.img_error)
                .into(iv_home)

        Picasso.get().load(awayBadge)
                .placeholder(R.drawable.img_placholder)
                .error(R.drawable.img_error)
                .into(iv_away)
    }

    override fun setEventDetail(event: Event?) {
        tv_home_team.text = event?.strHomeTeam ?: ""
        tv_away_team.text = event?.strAwayTeam ?: ""
        tv_date.text = event?.dateEvent ?: ""

        tv_shots_home.text = event?.intHomeShots ?: ""
        tv_shots_away.text = event?.intAwayShots ?: ""

        tv_score.text = getString(R.string.score, event?.intHomeScore ?: "", event?.intAwayScore ?: "")

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

    override fun showLoading(show: Boolean) {
        swipe?.isRefreshing = show
    }
}

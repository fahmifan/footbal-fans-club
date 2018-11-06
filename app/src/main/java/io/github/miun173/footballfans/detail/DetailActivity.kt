package io.github.miun173.footballfans.detail

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import com.squareup.picasso.Picasso
import io.github.miun173.footballfans.R
import io.github.miun173.footballfans.model.Event
import io.github.miun173.footballfans.repository.local.DBManagerImpl
import io.github.miun173.footballfans.repository.remote.FetchImpl
import kotlinx.android.synthetic.main.activity_detail.*
import org.jetbrains.anko.design.snackbar

class DetailActivity : AppCompatActivity(), DetailContract.View {

    lateinit var event: Event
    lateinit var presenter: DetailPresenter
    var eventID: Int = 0
    private var menuItem: Menu? = null
    private lateinit var mainLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        mainLayout = lin_layout_main

        setSupportActionBar(detail_toolbar)
        println("action bar showed >>> " + supportActionBar?.isShowing)

        event = intent.getParcelableExtra("event")
        eventID = event.idEvent?.toInt() ?: -1

        presenter = DetailPresenter(this, FetchImpl(), DBManagerImpl(applicationContext))

        presenter.getEventDetail(eventID)
        presenter.getTeam(event.strHomeTeam, event.strAwayTeam)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when(item?.itemId) {
        R.id.match_fav_menu -> {
            presenter.setFaved(event)
            true
        }

        else -> {
            false
        }
    }

    override fun showFav(show: Boolean) {
        if(show) {
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this,
                R.drawable.ic_baseline_favorite_24px)
            return
        }

        menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this,
                R.drawable.ic_baseline_favorite_border_24px)
    }

    override fun showSetFavSuccessfull() {
        snackbar(mainLayout, "Match added to favorite").show()
    }

    override fun showSetFavFailed() {
        snackbar(mainLayout, "unable add to favorite").show()
    }

    override fun showUsetFavFailed() {
        snackbar(mainLayout, "Match removed from favorite").show()
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
}

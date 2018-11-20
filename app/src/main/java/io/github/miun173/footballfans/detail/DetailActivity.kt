package io.github.miun173.footballfans.detail

//import android.support.v4.content.ContextCompat
//import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.squareup.picasso.Picasso
import io.github.miun173.footballfans.R
import io.github.miun173.footballfans.model.Event
import io.github.miun173.footballfans.repository.local.DBManagerImpl
import io.github.miun173.footballfans.repository.remote.MatchRepoImpl
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity(), DetailContract.View {
    private lateinit var event: Event
    private lateinit var presenter: DetailPresenter
    private var eventID: Int = 0
    private var menuItem: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        setSupportActionBar(detail_toolbar)
        println("action bar showed >>> " + supportActionBar?.isShowing)

        event = intent.getParcelableExtra("event")
        eventID = event.idEvent?.toInt() ?: -1

        presenter = DetailPresenter(this, MatchRepoImpl() ,DBManagerImpl(applicationContext))

        presenter.getEventDetail(eventID)
        presenter.getTeam(event.strHomeTeam, event.strAwayTeam)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when(item?.itemId) {
        R.id.menu_add_fav -> {
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
}

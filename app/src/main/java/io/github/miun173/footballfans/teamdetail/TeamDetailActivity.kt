package io.github.miun173.footballfans.teamdetail

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import io.github.miun173.footballfans.R
import io.github.miun173.footballfans.model.Team
import io.github.miun173.footballfans.repository.local.TeamLocalImpl
import io.github.miun173.footballfans.teamdetail.player.PlayerFragment
import kotlinx.android.synthetic.main.activity_team_detail.*

class TeamDetailActivity : AppCompatActivity(), TeamDetailContract.View {
    lateinit var team: Team
    private lateinit var teamAdapter: TeamFragmentAdapter
    private var menuItem: Menu? = null
    private lateinit var presenter: TeamDetailContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_detail)

        setSupportActionBar(detail_toolbar)
        println("action bar showed >>> " + supportActionBar?.isShowing)

        team = intent.getParcelableExtra(getString(R.string.intent_team))

        teamAdapter =  TeamFragmentAdapter(supportFragmentManager)
        vp_team_detail.adapter = teamAdapter
        tablayout_team_detail.setupWithViewPager(vp_team_detail)

        presenter = TeamDetailPresenter(this, TeamLocalImpl(applicationContext))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        this.menuItem = menu
        menuInflater.inflate(R.menu.fav_menu, menu)
        presenter.getFaved(team.teamId?.toInt() ?: 0)

        return menu != null
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId) {
            R.id.menu_add_fav -> {
                presenter.setFaved(team)
                true
            }
            else -> false
        }
    }

    override fun showFav(show: Boolean) {
        if(show) {
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this,
                    R.drawable.ic_baseline_favorite_24px)
        } else {
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this,
                    R.drawable.ic_baseline_favorite_border_24px)
        }

    }

    override fun showSetFavFailed() {
        Toast.makeText(applicationContext, "Unable add to favorite", Toast.LENGTH_SHORT).show()
    }

    override fun showUnsetFavFailed() {
        Toast.makeText(applicationContext, "Failed to removed from favorite", Toast.LENGTH_SHORT).show()
    }

    override fun showUnsetSuccess() {
        Toast.makeText(applicationContext, "Match removed from favorite", Toast.LENGTH_SHORT).show()
    }

    override fun showSetFavSuccess() {
        Toast.makeText(applicationContext, "Match added to favorite", Toast.LENGTH_SHORT).show()
    }

    inner class TeamFragmentAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            val args = Bundle()
            args.putParcelable(getString(R.string.intent_team), team)

            return when(position) {
                0 -> {
                    val teamOverview = TeamOverview()
                    teamOverview.arguments = args

                    teamOverview
                }
                else -> {
                    val playerFrag = PlayerFragment()
                    playerFrag.arguments = args

                    playerFrag
                }
            }
        }

        override fun getCount(): Int = 2

        override fun getPageTitle(position: Int): CharSequence? {
            return when(position) {
                0 -> "Team Overview"
                else -> "Players"
            }
        }

    }
}

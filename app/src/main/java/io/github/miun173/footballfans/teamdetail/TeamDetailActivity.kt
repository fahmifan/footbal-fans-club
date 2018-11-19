package io.github.miun173.footballfans.teamdetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import io.github.miun173.footballfans.R
import io.github.miun173.footballfans.model.Team
import io.github.miun173.footballfans.teamdetail.player.PlayerFragment
import kotlinx.android.synthetic.main.activity_team_detail.*

class TeamDetailActivity : AppCompatActivity() {
    lateinit var team: Team
    lateinit var teamAdapter: TeamFragmentAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_detail)

        team = intent.getParcelableExtra(getString(R.string.intent_team))

        teamAdapter =  TeamFragmentAdapter(supportFragmentManager)
        vp_team_detail.adapter = teamAdapter
        tablayout_team_detail.setupWithViewPager(vp_team_detail)
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
                1 -> {
                    val playerFrag = PlayerFragment()
                    playerFrag.arguments = args

                    playerFrag
                }
                else -> {
                    PlayerFragment()
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

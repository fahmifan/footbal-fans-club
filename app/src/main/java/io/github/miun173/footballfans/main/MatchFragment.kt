package io.github.miun173.footballfans.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import io.github.miun173.footballfans.R
import io.github.miun173.footballfans.main.schedule.ScheduleFragment
import kotlinx.android.synthetic.main.fragment_match.*

class MatchFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_match, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        match_pager.adapter = MatchFragmentAdapter(childFragmentManager)
        tablayout_match.setupWithViewPager(match_pager)
    }

    private inner class MatchFragmentAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            val sf = ScheduleFragment()
            return when(position) {
                0 -> {
                    sf.isNext = false
                    sf
                }
                else -> {
                    sf.isNext = true
                    sf
                }
            }
        }

        override fun getCount(): Int = 2

        override fun getPageTitle(position: Int): String {
            return when (position) {
                0 -> getString(R.string.match_past)
                else -> getString(R.string.match_next)
            }
        }
    }
}
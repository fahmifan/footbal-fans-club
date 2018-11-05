package io.github.miun173.footballfans.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.miun173.footballfans.R
import io.github.miun173.footballfans.model.Event

class FavmatchFragment: Fragment(), FavmatchContract.View {
    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favmatch, parent, false)
    }

    override fun setFavmatch(event: Event) {

    }

    private inner class PagerAdpater(fm: FragmentManager?): FragmentStatePagerAdapter(fm) {
        override fun getItem(pos: Int): Fragment {
            return when (pos) {
                0 -> {
                    val main = ScheduleFragment()
                    main.isNext = false
                    main
                }
                else -> {
                    val main = ScheduleFragment()
                    main.isNext = true
                    main
                }
            }
        }
        override fun getCount(): Int = 2
        override fun getPageTitle(position: Int): CharSequence? {
            return when(position) {
                0 -> "Prev Match"
                1 -> "Next Match"
                else -> "No Match"
            }
        }
    }
}
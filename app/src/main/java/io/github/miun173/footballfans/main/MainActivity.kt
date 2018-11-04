package io.github.miun173.footballfans.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import io.github.miun173.footballfans.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pager_main.adapter = MainPagerAdapter(supportFragmentManager)

        // Set view pager to use Tab
        tabs_main.setupWithViewPager(pager_main)
    }

    private inner class MainPagerAdapter(fm: FragmentManager): FragmentStatePagerAdapter(fm) {
        override fun getItem(pos: Int): Fragment {
            return when (pos) {
                0 -> {
                    val main = MainFragment()
                    main.isNext = false
                    main
                }
                else -> {
                    val main = MainFragment()
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

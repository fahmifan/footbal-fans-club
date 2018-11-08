package io.github.miun173.footballfans.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.github.miun173.footballfans.R
import io.github.miun173.footballfans.main.favmatch.FavmatchFragment
import io.github.miun173.footballfans.main.schedule.ScheduleFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val view = ScheduleFragment()
        view.isNext = false
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, view)
                .commit()

        bottom_nav.setOnNavigationItemSelectedListener {
            val scheduleFrag = ScheduleFragment()

            when(it.itemId) {
                R.id.nav_pastmatch -> {
                    scheduleFrag.isNext = false
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, scheduleFrag)
                            .commit()
                    true
                }

                R.id.nav_nextmatch -> {
                    scheduleFrag.isNext = true
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, scheduleFrag)
                            .commit()
                    true
                }

                R.id.nav_favmatch -> {
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, FavmatchFragment())
                            .commit()
                    true
                }

                else -> {
                    true
                }
            }
        }

        bottom_nav.setOnNavigationItemReselectedListener {
            when(it.itemId) {
                R.id.nav_nextmatch -> {
                }

                R.id.nav_favmatch -> {}

                R.id.nav_pastmatch -> {}
            }
        }
    }
}

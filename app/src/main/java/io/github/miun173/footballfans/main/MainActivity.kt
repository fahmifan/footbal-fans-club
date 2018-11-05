package io.github.miun173.footballfans.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.github.miun173.footballfans.R
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
            when(it.itemId) {
                R.id.nav_pastmatch -> {
                    val view = ScheduleFragment()
                    view.isNext = false
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, view)
                            .commit()
                    true
                }

                R.id.nav_nextmatch -> {
                    val view = ScheduleFragment()
                    view.isNext = true
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, view)
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
    }
}

package io.github.miun173.footballfans.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.github.miun173.footballfans.R
import io.github.miun173.footballfans.main.favmatch.FavmatchFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, MatchFragment())
                .commit()
        
        bottom_nav.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.nav_nextmatch -> {
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, MatchFragment())
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
                R.id.nav_nextmatch -> {}
                R.id.nav_favmatch -> {}
            }
        }
    }
}

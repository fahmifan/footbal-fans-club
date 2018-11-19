package io.github.miun173.footballfans.main

import android.content.Intent
import android.os.Bundle
import android.text.Selection.setSelection
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import io.github.miun173.footballfans.R
import io.github.miun173.footballfans.main.schedule.ScheduleFragment
import io.github.miun173.footballfans.main.searchresult.SearchedMatchctivity
import io.github.miun173.footballfans.model.League
import io.github.miun173.footballfans.repository.remote.MatchRepoImpl
import kotlinx.android.synthetic.main.fragment_match.*

class MatchFragment: Fragment() {
    lateinit var searchView: SearchView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_match, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        match_pager.adapter = MatchFragmentAdapter(childFragmentManager)
        tablayout_match.setupWithViewPager(match_pager)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.appbar_menu, menu)

        val searchItem = menu?.findItem(R.id.action_search)
        searchView = searchItem?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // show search result in new activity
                val intent = Intent(context, SearchedMatchctivity::class.java)
                intent.putExtra("query", query)
                startActivity(intent)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })

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
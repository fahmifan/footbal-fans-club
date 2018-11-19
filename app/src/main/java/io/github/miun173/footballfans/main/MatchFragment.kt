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

class MatchFragment: Fragment(), MatchContract.View, AdapterView.OnItemSelectedListener {
    lateinit var searchView: SearchView
    private val leagues: MutableList<League> = mutableListOf()
    lateinit var presenter: MatchContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        presenter = MatchPresenter(this, MatchRepoImpl())
//        presenter.getSpinner()
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

    override fun setSpinner(leagues: List<League>) {
        this.leagues.clear()
        this.leagues.addAll(leagues)

        val aa = ArrayAdapter<League>(context, android.R.layout.simple_spinner_item, this.leagues)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        match_spinner.apply {
            adapter = aa
            setSelection(0, false)
            onItemSelectedListener = this@MatchFragment
            prompt = "Select A League"
            gravity = Gravity.CENTER
        }

        val NEW_SPINNER_ID = 1
        Spinner(context).id = NEW_SPINNER_ID
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
        // repopulate recyclerview

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
package io.github.miun173.footballfans.main.fav

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.RecyclerView
import io.github.miun173.footballfans.R
import io.github.miun173.footballfans.main.EventsRVAdapter
import io.github.miun173.footballfans.main.fav.favmatch.FavMatchContract
import io.github.miun173.footballfans.main.fav.favmatch.FavMatchFragment
import io.github.miun173.footballfans.main.fav.favteam.FavTeamFragment
import io.github.miun173.footballfans.model.Event
import kotlinx.android.synthetic.main.fragment_fav.*

class FavFragment: Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_fav, parent, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vp_fav.let {
            it.adapter = FavPagerAdapter(childFragmentManager)
            tablayout_fav.setupWithViewPager(it)
        }
    }

    inner class FavPagerAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            return when(position) {
                0 -> FavMatchFragment()
                else -> FavTeamFragment()
            }
        }

        override fun getCount(): Int = 2

        override fun getPageTitle(position: Int): CharSequence? {
            return when(position) {
                0 -> "Match"
                else -> "Team"
            }
        }
    }
}
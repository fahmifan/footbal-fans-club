package io.github.miun173.footballfans.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.gson.Gson
import io.github.miun173.footballfans.Item
import io.github.miun173.footballfans.R
import io.github.miun173.footballfans.model.Team
import io.github.miun173.footballfans.repository.ApiRepo
import io.github.miun173.footballfans.repository.ApiRepoImpl
import kotlinx.android.extensions.CacheImplementation
import kotlinx.android.extensions.ContainerOptions

@ContainerOptions(cache = CacheImplementation.SPARSE_ARRAY)
class MainActivity : AppCompatActivity(), MainContract.View {
    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showTeamList(teams: List<Team>?) {
        println(teams.toString())
    }

    private var items: MutableList<Item> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val presenter = MainPresenter(this, ApiRepoImpl(), Gson())
        presenter.getTeamsLeague("English Premier League")

//        val ui = MainActivityUI().apply {
//            setContentView(this@MainActivity)
//            rvClub.layoutManager = LinearLayoutManager(this@MainActivity)
//        }
//
//        initData()
//
//        ui.rvClub.adapter = RecyclerViewAdapter(items) {
//            startActivity<ClubDetailActivity>(
//                    "image" to it.image,
//                    "name" to it.name,
//                    "detail" to it.details)
//        }

//        The above code is same as this

//        ui.rvClub.adapter = RecyclerViewAdapter(items, { items ->
//            startActivity<ClubDetailActivity>(
//                "image" to items.image,
//                "name" to items.name,
//                "detail" to items.details)
//
//        })
    }

//    class MainActivityUI : AnkoComponent<MainActivity> {
//        lateinit var rvClub: RecyclerView
//
//        override fun createView(ui: AnkoContext<MainActivity>) = with(ui){
//
//            verticalLayout {
//                recyclerView {
//                    padding = dip(4)
//                }.lparams(width = matchParent, height = matchParent)
//            }
//        }
//
//    }

    private fun initData() {
        val name = resources.getStringArray(R.array.club_name)
        val image = resources.obtainTypedArray(R.array.club_image)
        val details = resources.getStringArray(R.array.club_detail)

        items.clear()

        for (i in name.indices) {
            items.add(Item(name[i], image.getResourceId(i, 0), details[i]))
        }

        // Recycle the typed array
        image.recycle()
    }
}

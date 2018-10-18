package io.github.miun173.footballfans.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import io.github.miun173.footballfans.ClubDetailActivity
import io.github.miun173.footballfans.Item
import io.github.miun173.footballfans.R
import kotlinx.android.extensions.CacheImplementation
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import kotlinx.android.extensions.ContainerOptions

@ContainerOptions(cache = CacheImplementation.SPARSE_ARRAY)
class MainActivity : AppCompatActivity() {

    private var items: MutableList<Item> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val ui = MainActivityUI().apply {
            setContentView(this@MainActivity)
            rvClub.layoutManager = LinearLayoutManager(this@MainActivity)
        }

        initData()

        ui.rvClub.adapter = RecyclerViewAdapter(items) {
            startActivity(intentFor<ClubDetailActivity>(
                    "image" to it.image,
                    "name" to it.name,
                    "detail" to it.details)
            )
        }
    }

    class MainActivityUI : AnkoComponent<MainActivity> {
        lateinit var rvClub: RecyclerView

        override fun createView(ui: AnkoContext<MainActivity>) = with(ui){

            verticalLayout {
                rvClub = recyclerView {
                    padding = dip(4)
                }.lparams(width = matchParent, height = matchParent)
            }
        }

    }

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

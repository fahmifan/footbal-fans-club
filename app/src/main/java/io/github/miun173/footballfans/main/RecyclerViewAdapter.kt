package io.github.miun173.footballfans.main

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.squareup.picasso.Picasso
import io.github.miun173.footballfans.Item
import io.github.miun173.footballfans.R
import org.jetbrains.anko.*

class RecyclerViewAdapter(private val items: List<Item>, private val listener: (Item) -> Unit)
    : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(ClubDetailUI().createView(AnkoContext.create(parent.context, parent)))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], listener)
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val tvClubName: TextView = view.find(R.id.club_name)
        private val imvClubImage: ImageView = view.find(R.id.club_image)

        fun bind(items: Item, listener: (Item) -> Unit) {
                tvClubName.text = items.name
                Picasso.get().load(items.image).into(imvClubImage)

            itemView.setOnClickListener {
                listener(items)
            }
        }
    }

    inner class ClubDetailUI : AnkoComponent<ViewGroup> {

        override fun createView(ui: AnkoContext<ViewGroup>) = with(ui){
            verticalLayout {
                lparams(matchParent, wrapContent)
                padding = dip(16)
                orientation = LinearLayout.HORIZONTAL

                imageView {
                    id = R.id.club_image
                    layoutParams = LinearLayout.LayoutParams(matchParent, wrapContent)

                }.lparams {
                    height = dip(48)
                    width = dip(48)
                }

                textView {
                    id = R.id.club_name
                    layoutParams = LinearLayout.LayoutParams(matchParent, wrapContent)
                    text = "chelsea"
                    textSize = 14f
                }.lparams {
                    margin = dip(16)
                }
            }
        }

    }
}
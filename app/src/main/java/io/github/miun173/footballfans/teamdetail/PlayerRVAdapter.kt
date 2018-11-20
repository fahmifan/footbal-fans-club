package io.github.miun173.footballfans.teamdetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import io.github.miun173.footballfans.R
import io.github.miun173.footballfans.model.Player
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_team.*

class PlayerRVAdapter(private val players: MutableList<Player>,
                      private val listener: (Player) -> Unit)
: RecyclerView.Adapter<PlayerRVAdapter.ViewHolder>() {

    lateinit var parent: ViewGroup

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        this.parent = parent
        return ViewHolder(LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_team, parent, false))
    }

    override fun getItemCount(): Int = players.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(players[position], listener)
    }

    inner class ViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView),
            LayoutContainer {
        fun bind(player: Player, listener: (Player) -> Unit) {
            tv_name.text = player.strPlayer

            Picasso.get()
                    .load(player.strCutout ?: player.strThumb)
                    .placeholder(R.drawable.img_placholder)
                    .error(R.drawable.img_error)
                    .into(imv_pict)

            containerView.setOnClickListener {
                listener(player)
            }
        }
    }
}
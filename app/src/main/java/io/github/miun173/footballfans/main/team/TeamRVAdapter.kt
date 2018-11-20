package io.github.miun173.footballfans.main.team

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import io.github.miun173.footballfans.R
import io.github.miun173.footballfans.model.Team
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_team.*

class TeamRVAdapter(val teams: MutableList<Team>,
                    private val listener: (Team) -> Unit)
    : RecyclerView.Adapter<TeamRVAdapter.ViewHolder>() {

    lateinit var parent: ViewGroup

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        this.parent = parent
        return ViewHolder(LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_team, parent, false))
    }

    override fun getItemCount(): Int = teams.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(teams[position], listener)

    }

    inner class ViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView),
            LayoutContainer {
        fun bind(team: Team, listener: (Team) -> Unit) {

            Picasso.get().load(team.teamBadge)
                    .placeholder(R.drawable.img_placholder)
                    .error(R.drawable.img_error)
                    .into(imv_pict)

            tv_name.text = team.teamName

            containerView.setOnClickListener {
                listener(team)
            }
        }
    }
}
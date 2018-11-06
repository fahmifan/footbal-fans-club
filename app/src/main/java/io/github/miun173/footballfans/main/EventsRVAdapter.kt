package io.github.miun173.footballfans.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.miun173.footballfans.R
import io.github.miun173.footballfans.model.Event
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_event.*

class EventsRVAdapter(private val teams: MutableList<Event>, private val listener: (Event) -> Unit)
    : RecyclerView.Adapter<EventsRVAdapter.ViewHolder>() {

    lateinit var parent: ViewGroup

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        this.parent = parent
        return ViewHolder(LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_event,parent, false))
    }

    override fun getItemCount(): Int = teams.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(teams[position], listener)
    }

    inner class ViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        fun bind(event: Event, listener: (Event) -> Unit) {
            tv_team_home.text = event.strHomeTeam
            tv_team_away.text = event.strAwayTeam

            // gabisa import getString()
            tv_score.text = parent.context.getString(R.string.score, event.intHomeScore ?: "", event.intAwayScore ?: "")
            tv_date.text = event.dateEvent

            containerView.setOnClickListener {
                listener(event)
            }
        }
    }
}
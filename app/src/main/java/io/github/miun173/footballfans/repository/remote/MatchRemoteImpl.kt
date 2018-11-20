package io.github.miun173.footballfans.repository.remote

import com.google.gson.Gson
import io.github.miun173.footballfans.model.*
import io.github.miun173.footballfans.utils.DateTime
import java.net.URL

class MatchRemoteImpl: MatchRemote {

    override fun getPlayers(teamName: String): List<Player> {
        val res = Gson().fromJson(
                URL(TheSportDbRoute.getTeamPlayers(teamName)).readText(),
                Players::class.java
        )

        return res.players ?: emptyList()
    }

    override fun getTeamsLeague(id: Int): List<Team> {
        val res = Gson().fromJson(
                URL(TheSportDbRoute.getTeamsLeague(id)).readText(),
                Teams::class.java)

        return res.teams ?: emptyList()
    }

    override fun getLeagues(): List<League> {
        val res = Gson().fromJson(
                URL(TheSportDbRoute.getAllLeague()).readText(),
                Leagues::class.java)

        return res.leagues ?: emptyList()
    }

    override fun searchEvent(eventName: String): List<Event> {
        val res = Gson().fromJson(
                URL(TheSportDbRoute.searchEvent(eventName)).readText(),
                SearchEvent::class.java)

        return res.events ?: emptyList()
    }

    override fun getTeam(name: String): List<Team> {
        val res = Gson().fromJson(
                URL(TheSportDbRoute.getTeam(name)).readText(),
                Teams::class.java)

        return res.teams ?: emptyList()
    }

    override fun getEventDetail(id: Int): Event {
        val detail = Gson().fromJson(
                URL(TheSportDbRoute.getEventDetail(id)).readText(),
                Events::class.java)

        detail.events?.map {
            it.dateEvent = it.dateEvent?.let { it1 -> DateTime.getShortDate(it1) }
        }

        return detail?.events?.get(0) ?: Event()
    }

    override fun getLast15Events(id: Int): List<Event> {
        val res = Gson().fromJson(
                URL(TheSportDbRoute.getLast15Events(id.toString())).readText(),
                Events::class.java)

        res.events?.map {
            it.dateEvent = it.dateEvent?.let { it1 -> DateTime.getShortDate(it1) }
        }

        return res.events ?: emptyList()
    }

    override fun getNext15Events(id: Int): List<Event> {
        val res = Gson().fromJson(
                URL(TheSportDbRoute.getNext15Events(id.toString())).readText(),
                Events::class.java)

        res.events?.map {
            it.dateEvent = it.dateEvent?.let { it1 -> DateTime.getShortDate(it1) }
        }

        return res.events ?: emptyList()
    }
}
package io.github.miun173.footballfans.repository.remote

import io.github.miun173.footballfans.model.Event
import io.github.miun173.footballfans.model.League
import io.github.miun173.footballfans.model.Player
import io.github.miun173.footballfans.model.Team

interface MatchRepo {
    fun getTeam(name: String): List<Team>
    fun getTeamsLeague(id: Int): List<Team>
    fun getEventDetail(id: Int): Event
    fun getLast15Events(id: Int): List<Event>
    fun getNext15Events(id: Int): List<Event>
    fun searchEvent(eventName: String): List<Event>
    fun getLeagues(): List<League>
    fun getPlayers(teamName: String): List<Player>
}
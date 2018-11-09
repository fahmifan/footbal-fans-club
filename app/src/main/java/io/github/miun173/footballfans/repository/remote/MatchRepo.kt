package io.github.miun173.footballfans.repository.remote

import io.github.miun173.footballfans.model.Event
import io.github.miun173.footballfans.model.Team

interface MatchRepo {
    fun getTeam(name: String): List<Team>
    fun getEventDetail(id: Int): List<Event>
    fun getLast15Events(id: Int): List<Event>
    fun getNext15Events(id: Int): List<Event>
}
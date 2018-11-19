package io.github.miun173.footballfans.repository.remote

import android.net.Uri
import io.github.miun173.footballfans.BuildConfig

object TheSportDbRoute {
    val BASE_ROUTE = BuildConfig.BASE_URL + "api/v1/json/" + BuildConfig.TSDB_API_KEY

    fun getTeam(name: String?): String = BASE_ROUTE + "/searchteams.php?t=" + Uri.encode(name)
    fun getEventDetail(id: Int?): String = BASE_ROUTE + "/lookupevent.php?id=" + Uri.encode(id.toString())
    fun getLast15Events(id: String?): String = BASE_ROUTE + "/eventspastleague.php?id=" + Uri.encode(id)
    fun getNext15Events(id: String?): String = BASE_ROUTE + "/eventsnextleague.php?id=" + Uri.encode(id)
    fun searchEvent(name: String): String = BASE_ROUTE + "/searchevents.php?e=" + Uri.encode(name)
    fun getAllLeague(): String = BASE_ROUTE + "/all_leagues.php"
}
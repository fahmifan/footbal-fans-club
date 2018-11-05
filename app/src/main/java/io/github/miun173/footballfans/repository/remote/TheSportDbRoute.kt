package io.github.miun173.footballfans.repository.remote

import android.net.Uri
import io.github.miun173.footballfans.BuildConfig

object TheSportDbRoute {
    val BASE_ROUTE = BuildConfig.BASE_URL + "api/v1/json/" + BuildConfig.TSDB_API_KEY

    fun getTeam(name: String?): String {
        val url = BASE_ROUTE + "/searchteams.php?t=" + Uri.encode(name)

        println("url: $url")

        return url
    }

    fun getEventDetail(id: Int?): String {
        return BASE_ROUTE + "/lookupevent.php?id=" + Uri.encode(id.toString())
    }

    fun getLast15Events(id: String?): String {
        return BASE_ROUTE + "/eventspastleague.php?id=" + Uri.encode(id)
    }

    fun getNext15Events(id: String?): String {
        return BASE_ROUTE + "/eventsnextleague.php?id=" + Uri.encode(id)
    }
}
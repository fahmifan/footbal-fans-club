package io.github.miun173.footballfans.repository

import android.net.Uri
import io.github.miun173.footballfans.BuildConfig

object TheSportDbRoute {
    val BASE_ROUTE = Uri.parse(BuildConfig.BASE_URL).buildUpon()
            .appendPath("api")
            .appendPath("v1")
            .appendPath("json")
            .appendPath(BuildConfig.TSDB_API_KEY)

    val BASE_ROUTE_2 = BuildConfig.BASE_URL + "api/v1/json/" + BuildConfig.TSDB_API_KEY

    fun getTeam(name: String?): String {
        return BASE_ROUTE_2 + "/searchteams.php?t=" + Uri.parse(name).toString()
    }

    fun getLast15Events(id: String?): String {
        return BASE_ROUTE_2 + "/eventspastleague.php?id=" + Uri.parse(id).toString()
    }

    fun getNext15Events(id: String?): String {
        return BASE_ROUTE_2 + "/eventsnextleague.php?id=" + Uri.parse(id).toString()
    }
}
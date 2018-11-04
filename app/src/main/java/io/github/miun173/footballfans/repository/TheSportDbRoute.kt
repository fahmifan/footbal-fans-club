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

    fun getTeams(league: String?): String {
        val base =  BASE_ROUTE
        val url = base
                .appendPath("search_all_teams.php")
                .appendQueryParameter("l", league)
                .build()
                .toString()

        println("url >>> $url")
        return url
    }

    fun getTeam(name: String?): String {
        val url = BASE_ROUTE_2 + "/searchteams.php?t=" + Uri.parse(name).toString()
        println("getTeam() url >>> $url")
        return url
    }

    fun getLast15Events(id: String?): String {
//        val base = BASE_ROUTE
//        val url = base
//                .appendPath("eventspastleague.php")
//                .appendQueryParameter("id", id)
//                .build()
//                .toString()

        val url = BASE_ROUTE_2 + "/eventspastleague.php?id=" + Uri.parse(id).toString()

        println("url >>> $url")

        return url
    }
}
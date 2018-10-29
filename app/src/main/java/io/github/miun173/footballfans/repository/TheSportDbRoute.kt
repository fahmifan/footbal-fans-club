package io.github.miun173.footballfans.repository

import android.net.Uri
import io.github.miun173.footballfans.BuildConfig

object TheSportDbRoute {
    val BASE_ROUTE = Uri.parse(BuildConfig.BASE_URL).buildUpon()
            .appendPath("api")
            .appendPath("v1")
            .appendPath("json")
            .appendPath(BuildConfig.TSDB_API_KEY)

    fun getTeams(league: String?): String {
        return BASE_ROUTE
                .appendPath("search_all_teams.php")
                .appendQueryParameter("l", league)
                .build()
                .toString()
    }

    fun getLast15Events(id: String?): String {
        return BASE_ROUTE
                .appendPath("eventspastleague.php")
                .appendQueryParameter("id", id)
                .build()
                .toString()
    }
}
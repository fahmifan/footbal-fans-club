package io.github.miun173.footballfans.repository.remote

import android.net.Uri
import io.github.miun173.footballfans.BuildConfig
import io.github.miun173.footballfans.model.Team

class FootballApiImpl {
    val BASE_ROUTE = Uri.parse(BuildConfig.BASE_URL).buildUpon()
            .appendPath("api")
            .appendPath("v1")
            .appendPath("json")
            .appendPath(BuildConfig.TSDB_API_KEY)

    fun getTeam(name: String?): Team {

        return Team()
    }

}
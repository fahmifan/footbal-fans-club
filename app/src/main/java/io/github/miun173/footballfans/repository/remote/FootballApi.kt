package io.github.miun173.footballfans.repository.remote

import io.github.miun173.footballfans.model.Team
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FootballApi {
    @GET("searchteams.php")
    fun getTeam(@Query("t") t: String?): Call<List<Team>>
}
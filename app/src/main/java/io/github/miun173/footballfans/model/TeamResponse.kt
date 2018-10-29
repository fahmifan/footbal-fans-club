package io.github.miun173.footballfans.model

import com.google.gson.annotations.SerializedName

data class TeamResponse(
        @SerializedName("teams")
        val teams: List<Team>?
)
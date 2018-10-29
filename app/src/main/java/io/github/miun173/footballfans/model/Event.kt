package io.github.miun173.footballfans.model

import com.google.gson.annotations.SerializedName

data class Event(
        @SerializedName("idEvent")
        var idEvent: String? = "",

        @SerializedName("idLeague")
        var idLeague: String? = "",

        @SerializedName("strHomeTeam")
        var strHomeTeam: String? = "",

        @SerializedName("strAwayTeam")
        var strAwayTeam: String? = ""
)
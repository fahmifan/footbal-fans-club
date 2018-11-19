package io.github.miun173.footballfans.model

import com.google.gson.annotations.SerializedName

data class Leagues(
        @SerializedName("leagues")
        val leagues: List<League>?
)
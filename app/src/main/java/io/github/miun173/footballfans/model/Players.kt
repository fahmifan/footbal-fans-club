package io.github.miun173.footballfans.model

import com.google.gson.annotations.SerializedName

data class Players(
        @SerializedName("player")
        val players: List<Player>?
)
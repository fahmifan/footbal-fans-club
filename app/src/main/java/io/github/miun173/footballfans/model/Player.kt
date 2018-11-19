package io.github.miun173.footballfans.model

import com.google.gson.annotations.SerializedName

data class Player (
        @SerializedName("idPlayer")
        val idPlayer: String? = "",

        @SerializedName("idTeam")
        val idTeam: String? = "",

        @SerializedName("strTeam")
        val strTeam: String? = "",

        @SerializedName("strPlayer")
        val strPlayer: String? = "",

        @SerializedName("strDescriptionEN")
        val strDescriptionEN: String? = "",

        @SerializedName("strThumb")
        val strThumb: String? = "",

        @SerializedName("strCutout")
        val strCutout: String? = ""
)
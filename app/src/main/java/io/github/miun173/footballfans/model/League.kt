package io.github.miun173.footballfans.model

import com.google.gson.annotations.SerializedName

data class League(
        @SerializedName("idLeague")
        val idLeague: Int? = 0,

        @SerializedName("strLeague")
        val strLeague: String? = ""
) {
        override fun toString(): String = strLeague ?: ""
}
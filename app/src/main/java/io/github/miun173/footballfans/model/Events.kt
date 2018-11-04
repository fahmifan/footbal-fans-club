package io.github.miun173.footballfans.model

import com.google.gson.annotations.SerializedName

data class Events(
        @SerializedName("events")
        val events: List<Event>?
)
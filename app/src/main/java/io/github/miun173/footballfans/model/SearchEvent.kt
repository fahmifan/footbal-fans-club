package io.github.miun173.footballfans.model

import com.google.gson.annotations.SerializedName

data class SearchEvent(
        @SerializedName("event")
        val events: List<Event>?
)
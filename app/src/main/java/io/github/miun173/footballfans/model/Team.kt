package io.github.miun173.footballfans.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Team(
        @SerializedName("idTeam")
        var teamId: String? = "",

        @SerializedName("strTeam")
        var teamName: String? = "",

        @SerializedName("strTeamBadge")
        var teamBadge: String? = ""
) : Parcelable
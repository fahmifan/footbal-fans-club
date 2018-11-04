package io.github.miun173.footballfans.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Event(
    @SerializedName("idEvent")
    var idEvent: String? = "",

    @SerializedName("idLeague")
    var idLeague: String? = "",

    @SerializedName("strHomeTeam")
    var strHomeTeam: String? = "",

    @SerializedName("strHomeGoalDetails")
    var strHomeGoalDetails: String? = "",

    @SerializedName("dateEvent")
    var dateEvent: String? = "",

    @SerializedName("intHomeScore")
    var intHomeScore: String? = "",

    @SerializedName("strHomeLineupGoalkeeper")
    var strHomeLineupGoalkeeper: String? = "",

    @SerializedName("strHomeLineupDefense")
    var strHomeLineupDefense: String? = "",

    @SerializedName("strHomeLineupMidfield")
    var strHomeLineupMidfield: String? = "",

    @SerializedName("strHomeLineupForward")
    var strHomeLineupForward: String? = "",

    @SerializedName("strHomeLineupSubstitutes")
    var strHomeLineupSubstitutes: String? = "",

    @SerializedName("strAwayTeam")
    var strAwayTeam: String? = "",

    @SerializedName("intAwayScore")
    var intAwayScore: String? = "",

    @SerializedName("strAwayGoalDetails")
    var strAwayGoalDetails: String? = "",

    @SerializedName("strAwayLineupGoalkeeper")
    var strAwayLineupGoalkeeper: String? = "",

    @SerializedName("strAwayLineupDefense")
    var strAwayLineupDefense: String? = "",

    @SerializedName("strAwayLineupMidfield")
    var strAwayLineupMidfield: String? = "",

    @SerializedName("strAwayLineupForward")
    var strAwayLineupForward: String? = "",

    @SerializedName("strAwayLineupSubstitutes")
    var strAwayLineupSubstitutes: String? = ""

    ) : Parcelable
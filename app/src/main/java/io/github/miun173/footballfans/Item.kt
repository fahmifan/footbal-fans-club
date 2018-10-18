package io.github.miun173.footballfans

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Item(val name: String?, val image: Int?, val details: String?) : Parcelable
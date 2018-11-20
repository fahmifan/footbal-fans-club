package io.github.miun173.footballfans.repository.local

import io.github.miun173.footballfans.model.Event

interface MatchLocal {
    fun insertFavMatch(event: Event): Long
    fun deleteFavMatch(favID: Int): Int
    fun checkFav(id: Int): Boolean
    fun getFav(matchID: Int): List<DBContract.FavMatch>
    fun getFavs(): List<DBContract.FavMatch>
}
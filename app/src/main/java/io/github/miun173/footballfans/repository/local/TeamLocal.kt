package io.github.miun173.footballfans.repository.local

import io.github.miun173.footballfans.model.Team

interface TeamLocal {
    fun insertFavTeam(team: Team): Long
    fun deleteFavMatch(favID: Int): Int
    fun checkFav(teamID: Int): Boolean
    fun getFav(teamID: Int): List<DBContract.FavTeam>
    fun getFavs(): List<DBContract.FavTeam>
}
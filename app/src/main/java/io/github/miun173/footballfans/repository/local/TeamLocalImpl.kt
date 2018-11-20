package io.github.miun173.footballfans.repository.local

import android.content.Context
import android.database.SQLException
import io.github.miun173.footballfans.model.Team
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.db.delete

class TeamLocalImpl(private val ctx: Context): TeamLocal {
    override fun insertFavTeam(team: Team): Long {
        var newID: Long = -1

        return try {
            ctx.database.use {
                newID = insert(DBContract.FavTeam.TABLE_NAME,
                        DBContract.FavTeam.TEAM_ID to team.teamId)
            }

            newID

        } catch (e: SQLException) {
            e.printStackTrace()
            newID
        }
    }

    override fun deleteFavMatch(favID: Int): Int {
        var numRowsDeleted = -1
        try {
            ctx.database.use {
                numRowsDeleted = delete(DBContract.FavTeam.TABLE_NAME,
                        "${DBContract.FavTeam.ID} = {favID}",
                        "favID" to favID)
            }

            return numRowsDeleted

        } catch (e: SQLException) {
            e.printStackTrace()
            return numRowsDeleted
        }
    }

    override fun checkFav(teamID: Int): Boolean {
        try {
            var fav: List<DBContract.FavTeam> = ArrayList()
            ctx.database.use {
                val res = select(DBContract.FavTeam.TABLE_NAME)
                        .whereArgs("${DBContract.FavTeam.TEAM_ID} = {teamID}",
                                "teamID" to teamID)

                fav = res.parseList(classParser())
            }

            if(fav.isEmpty()) return false

            return true
        } catch (e: java.sql.SQLException) {
            e.printStackTrace()
            return false
        }
    }

    override fun getFav(teamID: Int): List<DBContract.FavTeam> {
        var fav: List<DBContract.FavTeam> = ArrayList()
        try {

            ctx.database.use {
                val res = select(DBContract.FavTeam.TABLE_NAME)
                        .whereArgs("${DBContract.FavTeam.TEAM_ID} = {teamID}",
                                "teamID" to teamID)

                fav = res.parseList(classParser())
            }

            if(fav.isEmpty()) return emptyList()

            return fav

        } catch (e: SQLException) {
            e.printStackTrace()
            return emptyList()
        }
    }

    override fun getFavs(): List<DBContract.FavTeam> {
        return try {
            var favs: List<DBContract.FavTeam> = ArrayList()

            ctx.database.use {
                val res = select(DBContract.FavTeam.TABLE_NAME)
                favs = res.parseList(classParser())
            }

            favs

        } catch (e: SQLException) {
            e.printStackTrace()
            emptyList()
        }
    }

}
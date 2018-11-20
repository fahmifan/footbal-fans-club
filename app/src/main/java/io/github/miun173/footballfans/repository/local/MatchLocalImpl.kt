package io.github.miun173.footballfans.repository.local

import android.content.Context
import io.github.miun173.footballfans.model.Event
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import java.sql.SQLException

class MatchLocalImpl(private val ctx: Context): MatchLocal {
    override fun getFavs(): List<DBContract.FavMatch> {
        try {
            var favs: List<DBContract.FavMatch> = ArrayList()

            ctx.database.use {
                val res = select(DBContract.FavMatch.TABLE_NAME)
                favs = res.parseList(classParser())
            }

            return favs

        } catch (e: SQLException) {
            e.printStackTrace()
            return emptyList()
        }
    }

    /**
     * Check if match already in faved by query matchID
     */
    override fun checkFav(id: Int): Boolean {
        try {
            var fav: List<DBContract.FavMatch> = ArrayList()
            ctx.database.use {
                val res = select(DBContract.FavMatch.TABLE_NAME)
                        .whereArgs("${DBContract.FavMatch.MATCH_ID} = {matchID}",
                                "matchID" to id)

                fav = res.parseList(classParser())
            }

            if(fav.isEmpty()) return false

            return true
        } catch (e: SQLException) {
            e.printStackTrace()
            return false
        }
    }

    override fun deleteFavMatch(favID: Int): Int {
        var numRowsDeleted = -1
        try {
            ctx.database.use {
                numRowsDeleted = delete(DBContract.FavMatch.TABLE_NAME,
                        "${DBContract.FavMatch.ID} = {favID}",
                        "favID" to favID)
            }

            return numRowsDeleted

        } catch (e: SQLException) {
            e.printStackTrace()
            return numRowsDeleted
        }
    }

    override fun insertFavMatch(event: Event): Long {
        var newID: Long = -1

        try {
            ctx.database.use {
                newID = insert(DBContract.FavMatch.TABLE_NAME,
                        DBContract.FavMatch.MATCH_ID to event.idEvent)
            }

            return newID

        } catch (e: SQLException) {
            e.printStackTrace()
            return newID
        }
    }

    override fun getFav(matchID: Int): List<DBContract.FavMatch> {
        var fav: List<DBContract.FavMatch> = ArrayList()
        try {

            ctx.database.use {
                val res = select(DBContract.FavMatch.TABLE_NAME)
                        .whereArgs("${DBContract.FavMatch.MATCH_ID} = {matchID}",
                                "matchID" to matchID)

                fav = res.parseList(classParser())
            }

            if(fav.isEmpty()) return emptyList()

            return fav

        } catch (e: SQLException) {
            e.printStackTrace()
            return emptyList()
        }
    }
}
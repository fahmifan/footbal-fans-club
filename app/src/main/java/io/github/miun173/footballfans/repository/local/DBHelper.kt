package io.github.miun173.footballfans.repository.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class DBHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, DBContract.DB_NAME, null,
        DBContract.DB_VERSION) {
    companion object {
        private var instance: DBHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): DBHelper {
            if (instance == null) {
                instance = DBHelper(ctx.applicationContext)
            }
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Here you create tables
        db.createTable(DBContract.FavMatch.TABLE_NAME, true,
                DBContract.FavMatch.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                DBContract.FavMatch.MATCH_ID to INTEGER)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Here you can upgrade tables, as usual
        db.dropTable(DBContract.FavMatch.TABLE_NAME, true)
    }
}

// Access property for Context
val Context.database: DBHelper
    get() = DBHelper.getInstance(applicationContext)
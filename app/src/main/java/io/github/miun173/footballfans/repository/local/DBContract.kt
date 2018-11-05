package io.github.miun173.footballfans.repository.local

class DBContract {
    companion object {
        const val DB_NAME = "footbal_match_scheduler"
        const val DB_VERSION = 1
    }

    data class FavMatch(val id: Long?,
                        val matchID: Long?) {

        companion object {
            const val TABLE_NAME = "fav_match"
            const val ID = "id"
            const val MATCH_ID = "match_id"
        }

    }
}
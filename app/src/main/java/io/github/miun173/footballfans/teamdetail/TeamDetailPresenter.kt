package io.github.miun173.footballfans.teamdetail

import io.github.miun173.footballfans.model.Team
import io.github.miun173.footballfans.repository.local.TeamLocal


class TeamDetailPresenter(private val view: TeamDetailContract.View,
                          private val teamLocal: TeamLocal)
    : TeamDetailContract.Presenter {

    override fun getFaved(teamID: Int) {
        val showFav =  teamLocal.checkFav(teamID)

        println("show fav >>> $showFav")

        view.showFav(showFav)
    }

    override fun setFaved(team: Team) {
        println("setFaved >>> begin")

        // get the faved data if exist
        val matchFav = teamLocal.getFav(team.teamId?.toInt() ?: -1)

        // if not exist (an emptyList) create one
        if(matchFav.isEmpty()) {
            team.let {
                val newID = teamLocal.insertFavTeam(it)

                if(newID.equals(-1)) {
                    // show failure message
                    view.showSetFavFailed()
                    println("unable to unfav team")

                    return
                }

                view.showSetFavSuccess()
                view.showFav(true)
            }

            println("setFaved >>> end")

            return
        }

        println("teamFav ID >>> ${matchFav[0].id}")

        // delete it!!
        val deletedID = teamLocal.deleteFavMatch(matchFav[0].id?.toInt() ?: -1)

        if(deletedID.equals(-1)) {
            // show failure message
            view.showUnsetFavFailed()
            println("unable to unset fav match")
            return
        }

        println("deletedID >>> $deletedID")

        view.showUnsetSuccess()
        view.showFav(false)

        println("setFaved >>> end")
    }

}
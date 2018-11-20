package io.github.miun173.footballfans.searchedteam

import io.github.miun173.footballfans.repository.remote.MatchRemote
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class SearchedTeamPresenter(val view: SearchedTeamContract.View,
                            val matchRemote: MatchRemote)
    : SearchedTeamContract.Presenter {

    override fun search(query: String) {
        view.setLoading(true)
        doAsync {
            val teams = matchRemote.getTeam(query)

            uiThread {
                if(teams.isEmpty()) {
                    view.setLoading(false)
                    view.setEmptyResult(true)
                } else {
                    view.setLoading(false)
                    view.setResult(teams)
                }
            }
        }
    }

}
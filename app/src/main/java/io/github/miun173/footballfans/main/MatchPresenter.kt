package io.github.miun173.footballfans.main

import io.github.miun173.footballfans.repository.remote.MatchRepo
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MatchPresenter(private val view: MatchContract.View,
                       private val matchRepo: MatchRepo)
    : MatchContract.Presenter {

    override fun getSpinner() {
        doAsync {

            val leagues = matchRepo.getLeagues()

            uiThread {
                if(!leagues.isEmpty()) {
                    view.setSpinner(leagues)
                }
            }
        }
    }
}
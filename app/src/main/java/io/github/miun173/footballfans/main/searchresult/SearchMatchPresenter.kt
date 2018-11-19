package io.github.miun173.footballfans.main.searchresult

import io.github.miun173.footballfans.repository.remote.MatchRepo
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class SearchMatchPresenter(private val view: SearchedMatchContract.View,
                           private val matchRepo: MatchRepo): SearchedMatchContract.Presenter {

    override fun search(query: String) {
        view.setLoading(true)
        doAsync {
            val events = matchRepo.searchEvent(query)

            uiThread {
                if(events.isEmpty()) {
                    view.setLoading(false)
                    view.setEmptyResult(true)
                } else {
                    view.setLoading(false)
                    view.setResult(events)
                }
            }
        }
    }

}
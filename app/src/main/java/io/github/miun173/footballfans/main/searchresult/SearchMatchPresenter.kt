package io.github.miun173.footballfans.main.searchresult

import io.github.miun173.footballfans.repository.remote.MatchRemote
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class SearchMatchPresenter(private val view: SearchedMatchContract.View,
                           private val matchRemote: MatchRemote): SearchedMatchContract.Presenter {

    override fun search(query: String) {
        view.setLoading(true)
        doAsync {
            val events = matchRemote.searchEvent(query)

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
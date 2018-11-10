package io.github.miun173.footballfans.main.favmatch

import io.github.miun173.footballfans.model.Event
import io.github.miun173.footballfans.repository.local.DBContract
import io.github.miun173.footballfans.repository.local.DBManager
import io.github.miun173.footballfans.repository.remote.MatchRepo
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import java.util.*

class FavmatchPresenterTest {
    // mocking
    @Mock private lateinit var view: FavmatchContract.View
    @Mock private lateinit var matchRepo: MatchRepo
    @Mock private lateinit var db: DBManager

    lateinit var presenter: FavmatchContract.Presenter

    val MANY_FAVS: List<DBContract.FavMatch> = Arrays.asList(
            DBContract.FavMatch(id = 1, matchID = 3041),
            DBContract.FavMatch(id = 2, matchID = 3042),
            DBContract.FavMatch(id = 3, matchID = 3043)
    )

    val NO_FAVS = emptyList<DBContract.FavMatch>()

    val MANY_EVENT: List<Event> = Arrays.asList(
            Event(),
            Event(),
            Event()
    )

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = FavmatchPresenter(view, matchRepo, db)
    }

    @Test
    fun show_favmatch_when_getFavmatch_notempty() {
        `when`(db.getFavs()).thenReturn(MANY_FAVS)

        presenter.getFavmatch()

        doAsync {
            `when`(db.getFavs()).thenReturn(MANY_FAVS)
            `when`(MANY_FAVS.map { matchRepo.getEventDetail(it.matchID?.toInt() ?: 0) })
                    .thenReturn(MANY_EVENT)

            uiThread {
                verify(view).showLoading(true)
                verify(view).setFavmatch(MANY_EVENT)
            }
        }

    }

    @Test
    fun show_nofavmatch_when_getFavs_empty() {
        `when`(db.getFavs()).thenReturn(NO_FAVS)

        presenter.getFavmatch()

        verify(view).showLoading(true)
        verify(view).showNoFav()

    }
}
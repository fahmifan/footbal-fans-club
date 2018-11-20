package io.github.miun173.footballfans.main.schedule

import io.github.miun173.footballfans.model.Event
import io.github.miun173.footballfans.repository.remote.MatchRemote
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import java.util.*

class SchedulePresenterTest {

    @Mock private lateinit var view: ScheduleContract.SchedulerView
    @Mock private lateinit var matchRemote: MatchRemote

    private lateinit var presenter: SchedulePresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = SchedulePresenter(view, matchRemote)
    }

    val MANY_EVENT: List<Event> = Arrays.asList(
            Event(),
            Event(),
            Event()
    )

    @Test
    fun showevents_when_getEvents_notempty() {
        `when`(matchRemote.getLast15Events(Mockito.anyInt())).thenReturn(MANY_EVENT)

        presenter.getEvents(Mockito.anyInt(), true)
        verify(view).showLoading(true)

        doAsync {
            `when`(matchRemote.getNext15Events(Mockito.anyInt())).thenReturn(MANY_EVENT)

            uiThread {
                verify(view).showEvents(MANY_EVENT)
            }
        }
    }
}
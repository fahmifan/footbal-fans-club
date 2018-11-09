package io.github.miun173.footballfans.main.schedule

import com.google.gson.Gson
import io.github.miun173.footballfans.model.Event
import io.github.miun173.footballfans.model.Events
import io.github.miun173.footballfans.repository.remote.Fetch
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
    @Mock private lateinit var fetch: Fetch

    private lateinit var presenter: SchedulePresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = SchedulePresenter(view, fetch)
    }

    val MANY_EVENT: List<Event> = Arrays.asList(
            Event(),
            Event(),
            Event()
    )

    val EVENTS : Events = Events(MANY_EVENT)

    val jsonEvent = """
        {"events":
            [
                {
                    "idEvent": "576586",
                    "idSoccerXML": "389971",
                    "strEvent": "Cardiff vs Brighton",
                    "strFilename": "English Premier League 2018-11-10 Cardiff vs Brighton",
                    "strSport": "Soccer",
                    "idLeague": "4328",
                    "strLeague": "English Premier League",
                    "strSeason": "1819",
                    "strDescriptionEN": null,
                    "strHomeTeam": "Cardiff",
                    "strAwayTeam": "Brighton",
                    "intHomeScore": null,
                    "intRound": "12",
                    "intAwayScore": null,
                    "intSpectators": null,
                    "strHomeGoalDetails": null,
                    "strHomeRedCards": null,
                    "strHomeYellowCards": null,
                    "strHomeLineupGoalkeeper": null,
                    "strHomeLineupDefense": null,
                    "strHomeLineupMidfield": null,
                    "strHomeLineupForward": null,
                    "strHomeLineupSubstitutes": null,
                    "strHomeFormation": null,
                    "strAwayRedCards": null,
                    "strAwayYellowCards": null,
                    "strAwayGoalDetails": null,
                    "strAwayLineupGoalkeeper": null,
                    "strAwayLineupDefense": null,
                    "strAwayLineupMidfield": null,
                    "strAwayLineupForward": null,
                    "strAwayLineupSubstitutes": null,
                    "strAwayFormation": null,
                    "intHomeShots": null,
                    "intAwayShots": null,
                    "dateEvent": "2018-11-10",
                    "strDate": "10/11/18",
                    "strTime": "12:30:00+00:00",
                    "strTVStation": null,
                    "idHomeTeam": "133637",
                    "idAwayTeam": "133619",
                    "strResult": null,
                    "strCircuit": null,
                    "strCountry": null,
                    "strCity": null,
                    "strPoster": null,
                    "strFanart": null,
                    "strThumb": null,
                    "strBanner": null,
                    "strMap": null,
                    "strLocked": "unlocked"
                },
                {
                    "idEvent": "576587",
                    "idSoccerXML": "389972",
                    "strEvent": "Newcastle vs Bournemouth",
                    "strFilename": "English Premier League 2018-11-10 Newcastle vs Bournemouth",
                    "strSport": "Soccer",
                    "idLeague": "4328",
                    "strLeague": "English Premier League",
                    "strSeason": "1819",
                    "strDescriptionEN": null,
                    "strHomeTeam": "Newcastle",
                    "strAwayTeam": "Bournemouth",
                    "intHomeScore": null,
                    "intRound": "12",
                    "intAwayScore": null,
                    "intSpectators": null,
                    "strHomeGoalDetails": null,
                    "strHomeRedCards": null,
                    "strHomeYellowCards": null,
                    "strHomeLineupGoalkeeper": null,
                    "strHomeLineupDefense": null,
                    "strHomeLineupMidfield": null,
                    "strHomeLineupForward": null,
                    "strHomeLineupSubstitutes": null,
                    "strHomeFormation": null,
                    "strAwayRedCards": null,
                    "strAwayYellowCards": null,
                    "strAwayGoalDetails": null,
                    "strAwayLineupGoalkeeper": null,
                    "strAwayLineupDefense": null,
                    "strAwayLineupMidfield": null,
                    "strAwayLineupForward": null,
                    "strAwayLineupSubstitutes": null,
                    "strAwayFormation": null,
                    "intHomeShots": null,
                    "intAwayShots": null,
                    "dateEvent": "2018-11-10",
                    "strDate": "10/11/18",
                    "strTime": "15:00:00+00:00",
                    "strTVStation": null,
                    "idHomeTeam": "134777",
                    "idAwayTeam": "134301",
                    "strResult": null,
                    "strCircuit": null,
                    "strCountry": null,
                    "strCity": null,
                    "strPoster": null,
                    "strFanart": null,
                    "strThumb": null,
                    "strBanner": null,
                    "strMap": null,
                    "strLocked": "unlocked"
                },
                {
                    "idEvent": "576589",
                    "idSoccerXML": "389974",
                    "strEvent": "Southampton vs Watford",
                    "strFilename": "English Premier League 2018-11-10 Southampton vs Watford",
                    "strSport": "Soccer",
                    "idLeague": "4328",
                    "strLeague": "English Premier League",
                    "strSeason": "1819",
                    "strDescriptionEN": null,
                    "strHomeTeam": "Southampton",
                    "strAwayTeam": "Watford",
                    "intHomeScore": null,
                    "intRound": "12",
                    "intAwayScore": null,
                    "intSpectators": null,
                    "strHomeGoalDetails": null,
                    "strHomeRedCards": null,
                    "strHomeYellowCards": null,
                    "strHomeLineupGoalkeeper": null,
                    "strHomeLineupDefense": null,
                    "strHomeLineupMidfield": null,
                    "strHomeLineupForward": null,
                    "strHomeLineupSubstitutes": null,
                    "strHomeFormation": null,
                    "strAwayRedCards": null,
                    "strAwayYellowCards": null,
                    "strAwayGoalDetails": null,
                    "strAwayLineupGoalkeeper": null,
                    "strAwayLineupDefense": null,
                    "strAwayLineupMidfield": null,
                    "strAwayLineupForward": null,
                    "strAwayLineupSubstitutes": null,
                    "strAwayFormation": null,
                    "intHomeShots": null,
                    "intAwayShots": null,
                    "dateEvent": "2018-11-10",
                    "strDate": "10/11/18",
                    "strTime": "15:00:00+00:00",
                    "strTVStation": null,
                    "idHomeTeam": "134778",
                    "idAwayTeam": "133624",
                    "strResult": null,
                    "strCircuit": null,
                    "strCountry": null,
                    "strCity": null,
                    "strPoster": null,
                    "strFanart": null,
                    "strThumb": null,
                    "strBanner": null,
                    "strMap": null,
                    "strLocked": "unlocked"
                },
            ]
        }
    """.trimIndent()

    @Test
    fun showevents_when_getEvents_notempty() {
        `when`(Gson().fromJson(jsonEvent,
                Events::class.java
        )).thenReturn(EVENTS)

        presenter.getEvents(Mockito.anyString(), true)

        verify(view).showLoading(true)
        verify(view).showEvents(MANY_EVENT)
    }
}
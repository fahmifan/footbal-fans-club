package io.github.miun173.footballfans.main.favmatch

import com.google.gson.Gson
import io.github.miun173.footballfans.model.Event
import io.github.miun173.footballfans.model.Events
import io.github.miun173.footballfans.repository.local.DBContract
import io.github.miun173.footballfans.repository.local.DBManager
import io.github.miun173.footballfans.repository.remote.Fetch
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
    @Mock private lateinit var fetch: Fetch
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

    val EVENTS : Events = Events(MANY_EVENT)

    val jsonEvent = """
        {"teams": [
            {
            "idTeam": "133604",
            "idSoccerXML": "9",
            "intLoved": "1",
            "strTeam": "Arsenal",
            "strTeamShort": "Ars",
            "strAlternate": "Gunners",
            "intFormedYear": "1892",
            "strSport": "Soccer",
            "strLeague": "English Premier League",
            "idLeague": "4328",
            "strDivision": null,
            "strManager": "Unai Emery",
            "strStadium": "Emirates Stadium",
            "strKeywords": "Gunners Gooners",
            "strRSS": "http://www.football365.com/arsenal/rss",
            "strStadiumThumb": "https://www.thesportsdb.com/images/media/team/stadium/qpuxrr1419371354.jpg",
            "strStadiumDescription": "The Emirates Stadium (known as Ashburton Grove prior to sponsorship) is a football stadium in Holloway, London, England, and the home of Arsenal Football Club. With a capacity of 60,272, the Emirates is the third-largest football stadium in England after Wembley and Old Trafford.In 1997, Arsenal explored the possibility of relocating to a new stadium, having been denied planning permission by Islington Council to expand its home ground of Highbury. After considering various options (including purchasing Wembley), the club bought an industrial and waste disposal estate in Ashburton Grove in 2000. A year later they won the council's approval to build a stadium on the site; manager Arsène Wenger described this as the \"biggest decision in Arsenal's history\" since the board appointed Herbert Chapman. Relocation began in 2002, but financial difficulties delayed work until February 2004. Emirates Airline was later announced as the main sponsor for the stadium. Work was completed in 2006 at a cost of £390 million.",
            "strStadiumLocation": "Holloway, London",
            "intStadiumCapacity": "60338",
            "strWebsite": "www.arsenal.com",
            "strFacebook": "www.facebook.com/Arsenal",
            "strTwitter": "twitter.com/arsenal",
            "strInstagram": "instagram.com/arsenal",
            "strDescriptionEN": "Arsenal Football Club is a professional football club based in Holloway, London which currently plays in the Premier League, the highest level of English football. One of the most successful clubs in English football, they have won 13 First Division and Premier League titles and a joint record 11 FA Cups.\r\n\r\nArsenal's success has been particularly consistent: the club has accumulated the second most points in English top-flight football, hold the ongoing record for the longest uninterrupted period in the top flight, and would be placed first in an aggregated league of the entire 20th century. Arsenal is the second side to complete an English top-flight season unbeaten (in the 2003–04 season), playing almost twice as many matches as the previous invincibles Preston North End in the 1888–89 season.\r\n\r\nArsenal was founded in 1886 in Woolwich and in 1893 became the first club from the south of England to join the Football League. In 1913, they moved north across the city to Arsenal Stadium in Highbury. In the 1930s, they won five League Championship titles and two FA Cups. After a lean period in the post-war years they won the League and FA Cup Double, in the 1970–71 season, and in the 1990s and first decade of the 21st century, won two more Doubles and reached the 2006 UEFA Champions League Final. Since neighbouring Tottenham Hotspur, the two clubs have had a fierce rivalry, the North London derby.\r\n\r\nArsenal have one of the highest incomes and largest fanbases in the world. The club was named the fifth most valuable association football club in the world, valued at £1.3 billion in 2014.",
            "strDescriptionDE": "Der FC Arsenal (offiziell: Arsenal Football Club) – auch bekannt als (The) Arsenal, (The) Gunners (deutsche Übersetzung: „Schützen“ oder „Kanoniere“) oder im deutschen Sprachraum auch Arsenal London genannt – ist ein 1886 gegründeter Fußballverein aus dem Ortsteil Holloway des Nordlondoner Bezirks Islington. Mit 13 englischen Meisterschaften und elf FA-Pokalsiegen zählt der Klub zu den erfolgreichsten englischen Fußballvereinen.Erst über 40 Jahre nach der Gründung gewann Arsenal mit fünf Ligatiteln und zwei FA Cups in den 1930er Jahren seine ersten bedeutenden Titel. Der nächste Meilenstein war in der Saison 1970/71 der Gewinn des zweiten englischen „Doubles“ im 20. Jahrhundert. In den vergangenen 20 Jahren etablierte sich Arsenal endgültig als einer der erfolgreichsten englischen Fußballvereine, und beim Gewinn zweier weiterer Doubles zu Beginn des 21. Jahrhunderts blieb die Mannschaft in der Ligasaison 2003/04 als zweite in der englischen Fußballgeschichte ungeschlagen. Zunehmende europäische Ambitionen unterstrich der Verein in der Spielzeit 2005/06, als Arsenal als erster Londoner Verein das Finale der Champions League erreichte.",
            "strDescriptionFR": null,
            "strDescriptionCN": null,
            "strDescriptionIT": "L'Arsenal Football Club, noto semplicemente come Arsenal, è una società calcistica inglese con sede a Londra, più precisamente nel quartiere di Holloway, nel borgo di Islington.[3]\r\n\r\nFondato nel 1886, è uno dei quattordici club che rappresentano la città di Londra a livello professionistico,[4] nonché uno dei più antichi del Paese. Milita nella massima serie del calcio inglese ininterrottamente dal 1919-1920, risultando quindi la squadra da più tempo presente in First Division/Premier League. È la prima squadra della capitale del Regno Unito per successi sportivi e, in ambito federale, la terza dopo Manchester United e Liverpool, essendosi aggiudicata nel corso della sua storia tredici campionati inglesi, dodici FA Cup (record di vittorie, condiviso con il Manchester United), due League Cup e quattordici Community Shield (una condivisa),[5] mentre in ambito internazionale ha conquistato una Coppa delle Coppe ed una Coppa delle Fiere. Inoltre è una delle tredici squadre che hanno raggiunto le finali di tutte le tre principali competizioni gestite dalla UEFA: Champions League (2005-2006), Coppa UEFA (1999-2000) e Coppa delle Coppe (1979-1980, 1993-1994 e 1994-1995).[6]\r\n\r\nI colori sociali, rosso per la maglietta e bianco per i pantaloncini, hanno subìto variazioni più o meno evidenti nel corso degli anni. Anche la sede del club è stata cambiata più volte: inizialmente la squadra giocava a Woolwich, ma nel 1913 si spostò all'Arsenal Stadium, nel quartiere di Highbury; dal 2006 disputa invece le sue partite casalinghe nel nuovo Emirates Stadium. Lo stemma è stato modificato ripetutamente, ma al suo interno è sempre comparso almeno un cannone. Proprio per questo motivo i giocatori ed i tifosi dell'Arsenal sono spesso soprannominati Gunners (in italiano \"cannonieri\").\r\n\r\nL'Arsenal conta su una schiera di tifosi molto nutrita, distribuita in ogni parte del mondo. Nel corso degli anni sono sorte profonde rivalità con i sostenitori di club concittadini, la più sentita delle quali è quella con i seguaci del Tottenham Hotspur, con i quali i Gunners giocano regolarmente il North London derby.[7] L'Arsenal è anche uno dei club più ricchi del mondo, con un patrimonio stimato di 1,3 miliardi di dollari, secondo la rivista Forbes nel 2014, facendone il quinto club più ricco del pianeta e il secondo in Inghilterra.[8]",
            "strDescriptionJP": null,
            "strDescriptionRU": null,
            "strDescriptionES": null,
            "strDescriptionPT": null,
            "strDescriptionSE": null,
            "strDescriptionNL": null,
            "strDescriptionHU": null,
            "strDescriptionNO": null,
            "strDescriptionIL": null,
            "strDescriptionPL": null,
            "strGender": "Male",
            "strCountry": "England",
            "strTeamBadge": "https://www.thesportsdb.com/images/media/team/badge/vrtrtp1448813175.png",
            "strTeamJersey": "https://www.thesportsdb.com/images/media/team/jersey/kzne111510861290.png",
            "strTeamLogo": "https://www.thesportsdb.com/images/media/team/logo/q2mxlz1512644512.png",
            "strTeamFanart1": "https://www.thesportsdb.com/images/media/team/fanart/xyusxr1419347566.jpg",
            "strTeamFanart2": "https://www.thesportsdb.com/images/media/team/fanart/qttspr1419347612.jpg",
            "strTeamFanart3": "https://www.thesportsdb.com/images/media/team/fanart/uwssqx1420884450.jpg",
            "strTeamFanart4": "https://www.thesportsdb.com/images/media/team/fanart/qtprsw1420884964.jpg",
            "strTeamBanner": "https://www.thesportsdb.com/images/media/team/banner/rtpsrr1419351049.jpg",
            "strYoutube": "www.youtube.com/user/ArsenalTour",
            "strLocked": "unlocked"
            },
            {
            "idTeam": "134301",
            "idSoccerXML": "756",
            "intLoved": "1",
            "strTeam": "Bournemouth",
            "strTeamShort": null,
            "strAlternate": "",
            "intFormedYear": "1890",
            "strSport": "Soccer",
            "strLeague": "English Premier League",
            "idLeague": "4328",
            "strDivision": null,
            "strManager": "Eddie Howe",
            "strStadium": "Dean Court",
            "strKeywords": "Cherries",
            "strRSS": "",
            "strStadiumThumb": "https://www.thesportsdb.com/images/media/team/stadium/rxvwvw1420707921.jpg",
            "strStadiumDescription": "Dean Court, known as the Goldsands Stadium for sponsorship purposes, is a football stadium in Bournemouth, England and the home ground of A.F.C. Bournemouth.\r\n\r\nIn 1910 Boscombe F.C. was given a piece of land by the town's Cooper-Dean family, after whom the ground was named. The land was the site of an old gravel pit, and the ground was not built in time for the start of the 1910–11 season. As a result, the club played at the adjacent King's Park until moving into Dean Court in December 1910. However, the club facilities were still not ready, and players initially had to change in a nearby hotel. Early developments at the ground included a 300-seat stand.\r\n\r\nIn 1923 the club were elected to Division Three South of the Football League, at which point they changed their name to Bournemouth & Boscombe Athletic. The first Football League match was played at Dean Court on 1 September 1923, with 7,000 watching a 0–0 draw with Swindon Town. Subsequent ground improvements were made following the purcase of fittings from the British Empire Exhibition at Wembley, which allowed the construction of a 3,700-seat stand. A covered terrace was added at the southern end of the ground in 1936.\r\n\r\nThe club's record League attendance was set on 14 April 1948, when 25,495 watched a 1–0 defeat to QPR. The overall record attendance was set on 2 March 1957, when 28,799 spectators watched an FA Cup match against Manchester United. Shortly afterwards, a roof was added to the western stand. The club also purchased more land behind the northern end of the ground, with the intention of enlarging the stand and building a leisure centre. However, the club ran out of money during its construction and abandoned the scheme in 1984. As a result, the half-built structure was demolished and housing was built on that part of the site. The club's lowest Football League attendance was set on 4 March 1986, when only 1,873 saw a 2–2 drawn with Lincoln City.\r\n\r\nThe ground was completely rebuilt in 2001, with the pitch rotated ninety degrees from its original position and the ground moved away from adjacent housing. Because the work was not finished in time for the start of the 2001–02 season, Bournemouth played their first eight games at the Avenue Stadium in nearby Dorchester. When Dean Court reopened with a game against Wrexham on 10 November, it gained its first sponsored name, becoming the Fitness First Stadium. Although it was rebuilt as a three sided stadium with a capacity of 9,600, seats were placed on the undeveloped south end in the autumn of 2005. On 24 February 2004 Bournemouth's James Hayter scored the Football League's fastest-ever hat-trick at Dean Court, scoring three goals in 2 minutes and 20 seconds during a 6–0 against Wrexham. The club sold the stadium in December 2005 in a sale-and-leaseback deal with London property company Structadene.\r\n\r\nIn the 2010–11 a temporary south stand was built, but was removed during the 2011–12 season after attendances fell. In July 2011 the stadium was renamed the Seward Stadium after the naming rights were sold to the Seward Motor Group. Following Seward entering administration in February 2012, the ground was subsequently renamed the Goldsands Stadium in a two-year deal. During the summer of 2013 a 2,400 seat stand was built on the undeveloped end of the ground as a result of the club's promotion to the Championship. In July 2013 it was named after former club striker Ted MacDougall.",
            "strStadiumLocation": "Bournemouth Dorset, England",
            "intStadiumCapacity": "11464",
            "strWebsite": "www.afcb.premiumtv.co.uk",
            "strFacebook": "",
            "strTwitter": "",
            "strInstagram": "",
            "strDescriptionEN": "A.F.C. Bournemouth is a football club playing in the Championship, the second tier in the English football league system. The club plays at Dean Court in Kings Park, Boscombe, Bournemouth, Dorset and have been in existence since 1899.\r\n\r\nNicknamed The Cherries, the team traditionally played in red shirts with white sleeves until 1971, when the strip was changed to red and black stripes, similar to that of A.C. Milan. A predominantly red shirt was chosen for the 2004–05 and 2005–06 seasons before announcing a return to the stripes for the 2006–07 season due to fan demand.\r\n\r\nAfter narrowly avoiding relegation from the Football League in the 2008–09 season, Bournemouth were promoted to League One at the end of the 2009–10. After making the League One play-off semi-finals in 2010–11 and achieving a mid-table finish in 2011–12, Bournemouth won promotion to the Championship at the end of the 2012–13 season, putting them in the second tier of the league for only the second time in their history.\r\n\r\nAlthough the exact date of the club's foundation is not known, there is proof that it was formed in the autumn of 1899 out of the remains of the older Boscombe St. John's Lads’ Institute F.C. The club was originally known as Boscombe F.C.. The first President was Mr. J.C. Nutt.\r\n\r\nIn their first season 1889–90 Boscombe F.C. competed in the Bournemouth and District Junior League. They also played in the Hants Junior Cup. During the first two seasons they played on a football pitch in Castlemain Avenue, Pokesdown. From their third season the team played on a pitch in King's Park. In the season of 1905–06 Boscombe F.C. graduated to senior amateur football.\r\n\r\nIn 1910 the club was granted a long lease upon some wasteland next to Kings Park, as the clubs football ground, by their president Mr. J.E. Cooper-Dean. With their own ground, named Dean Court after the benefactor, the club continued to thrive and dominated the local football scene. Also in 1910 the club signed their first professional football player B. Penton.\r\n\r\nAround about this time the club obtained their nickname 'The Cherries'. Foremost there are two tales on how the club gained this pet name. First, because of the cherry-red striped shirts that the team played in and, perhaps more plausible, because Dean Court was built adjacent to the Cooper-Dean estate, which encompassed numerous cherry orchards.\r\n\r\nFor the first time during the season of 1913–14 the club competed in the F.A. Cup. The clubs progress was halted in 1914 with the outbreak of the war and Boscombe F.C. returned to the Hampshire league.\r\n\r\nIn 1920 the Third Division was formed and Boscombe were promoted to the Southern League, with moderate success.",
            "strDescriptionDE": null,
            "strDescriptionFR": null,
            "strDescriptionCN": null,
            "strDescriptionIT": null,
            "strDescriptionJP": null,
            "strDescriptionRU": null,
            "strDescriptionES": null,
            "strDescriptionPT": null,
            "strDescriptionSE": null,
            "strDescriptionNL": null,
            "strDescriptionHU": null,
            "strDescriptionNO": null,
            "strDescriptionIL": null,
            "strDescriptionPL": null,
            "strGender": "Male",
            "strCountry": "England",
            "strTeamBadge": "https://www.thesportsdb.com/images/media/team/badge/y08nak1534071116.png",
            "strTeamJersey": "https://www.thesportsdb.com/images/media/team/jersey/aaonqz1510842509.png",
            "strTeamLogo": "https://www.thesportsdb.com/images/media/team/logo/yttutv1448813203.png",
            "strTeamFanart1": "https://www.thesportsdb.com/images/media/team/fanart/wvuypx1469485789.jpg",
            "strTeamFanart2": "https://www.thesportsdb.com/images/media/team/fanart/sxrxwp1469485821.jpg",
            "strTeamFanart3": "https://www.thesportsdb.com/images/media/team/fanart/uqqwvw1469485695.jpg",
            "strTeamFanart4": "https://www.thesportsdb.com/images/media/team/fanart/uwryss1469485747.jpg",
            "strTeamBanner": "https://www.thesportsdb.com/images/media/team/banner/sxypup1469486566.jpg",
            "strYoutube": "",
            "strLocked": "unlocked"
            },
            {
            "idTeam": "133619",
            "idSoccerXML": "24",
            "intLoved": null,
            "strTeam": "Brighton",
            "strTeamShort": null,
            "strAlternate": "Brighton and Hove Albion",
            "intFormedYear": "1901",
            "strSport": "Soccer",
            "strLeague": "English Premier League",
            "idLeague": "4328",
            "strDivision": null,
            "strManager": "Chris Hughton",
            "strStadium": "Falmer Stadium",
            "strKeywords": "",
            "strRSS": "",
            "strStadiumThumb": "https://www.thesportsdb.com/images/media/team/stadium/wqvxrw1420720245.jpg",
            "strStadiumDescription": "Falmer Stadium, known for sponsorship purposes as the American Express Community Stadium, or simply The Amex, is a football stadium near the village of Falmer in Brighton and Hove and is the home of Brighton & Hove Albion F.C.. The stadium was handed over from the developers to the club on 31 May 2011. The first competitive game to be played at the stadium was the 2010–11 season final of the Sussex Senior Cup between Brighton and Eastbourne Borough on 16 July 2011. The first ever league game was against Doncaster Rovers, who were also the opponents in the last ever game played at Brighton's former stadium, the Goldstone Ground, 14 years earlier.\r\n\r\nIn May 2013 it was announced that the stadium would host games during the 2015 Rugby World Cup.",
            "strStadiumLocation": "Falmer, Brighton, East Sussex, England",
            "intStadiumCapacity": "0",
            "strWebsite": "www.seagulls.co.uk",
            "strFacebook": "",
            "strTwitter": "",
            "strInstagram": "",
            "strDescriptionEN": "Brighton and Hove Albion Football Club /ˈbraɪtən ən ˈhoʊv/ is an English football club based in the coastal city of Brighton & Hove, East Sussex. It is often referred to just as Brighton. They currently play in the Football League Championship, the second tier of the English football league system.\r\n\r\nThe team is nicknamed the \"Seagulls\" or \"Albion\". The team has historically played in blue and white stripes, though this changed to all white briefly in the 1970s and again to plain blue during the club's most successful spell in the 1980s. Crystal Palace is considered the club's main rival, although the grounds are 40 miles apart.\r\n\r\nFounded in 1901, Brighton played their early professional football in the Southern League before being elected to the Football League in 1920. The club enjoyed greatest prominence between 1979 and 1983 when they played in the First Division and reached the 1983 FA Cup Final, losing to Manchester United after a replay. They were relegated from the top division in the same season. Mismanagement brought Brighton close to relegation from the Football League to the Conference which they narrowly avoided in 1997 and 1998. A boardroom takeover saved Brighton from liquidation, and following successive promotions they returned to the second tier of English football in 2002 and have played in the second and third tiers ever since.",
            "strDescriptionDE": null,
            "strDescriptionFR": null,
            "strDescriptionCN": null,
            "strDescriptionIT": null,
            "strDescriptionJP": null,
            "strDescriptionRU": null,
            "strDescriptionES": null,
            "strDescriptionPT": null,
            "strDescriptionSE": null,
            "strDescriptionNL": null,
            "strDescriptionHU": null,
            "strDescriptionNO": null,
            "strDescriptionIL": null,
            "strDescriptionPL": null,
            "strGender": "Male",
            "strCountry": "England",
            "strTeamBadge": "https://www.thesportsdb.com/images/media/team/badge/ywypts1448810904.png",
            "strTeamJersey": "https://www.thesportsdb.com/images/media/team/jersey/uvwprx1454114550.png",
            "strTeamLogo": "https://www.thesportsdb.com/images/media/team/logo/xvvqxv1448810981.png",
            "strTeamFanart1": "https://www.thesportsdb.com/images/media/team/fanart/bk2b6j1504211012.jpg",
            "strTeamFanart2": "https://www.thesportsdb.com/images/media/team/fanart/2f41gp1504211039.jpg",
            "strTeamFanart3": "https://www.thesportsdb.com/images/media/team/fanart/bfasc71504211054.jpg",
            "strTeamFanart4": "https://www.thesportsdb.com/images/media/team/fanart/nfo32o1504211067.jpg",
            "strTeamBanner": "https://www.thesportsdb.com/images/media/team/banner/5jitnu1504211082.jpg",
            "strYoutube": "",
            "strLocked": "unlocked"
            },
            {
            "idTeam": "133623",
            "idSoccerXML": "28",
            "intLoved": "0",
            "strTeam": "Burnley",
            "strTeamShort": null,
            "strAlternate": "",
            "intFormedYear": "1882",
            "strSport": "Soccer",
            "strLeague": "English Premier League",
            "idLeague": "4328",
            "strDivision": null,
            "strManager": "Sean Dyche",
            "strStadium": "Turf Moor",
            "strKeywords": "",
            "strRSS": "http://www.football365.com/burnley/rss",
            "strStadiumThumb": "https://www.thesportsdb.com/images/media/team/stadium/vvpvxw1420243385.jpg",
            "strStadiumDescription": "Turf Moor is a football stadium in Burnley, Lancashire, England. It is the home ground of Premier League side Burnley Football Club, who have played there since moving from its Calder Vale ground in 1883. The stadium, which is situated on Harry Potts Way, named so after the club's longest serving Manager, has a capacity of 21,401, all seated. It was one of the last remaining stadiums in England to have the players' tunnel and dressing rooms behind one of the goals, until it was covered for seating in time for the 2014-15 Premier League season and rebuilt between the David Fishwick and James Hargreaves stand. The ground originally consisted of just a pitch and the first grandstand was not built until 1885. Six years after this, the \"Star\" stand was erected and terracing was later added to the ends of the ground. After the Second World War, the stadium was redeveloped with all four stands being rebuilt. During the 1990s, the ground underwent further refurbishment when the Longside and Bee Hole End terraces were replaced by all-seater stands. Currently, the four stands at Turf Moor are the James Hargreaves Stand, the Jimmy McIlroy Stand, the Bob Lord Stand and the Cricket Field Stand.\r\n\r\nBurnley played their first match at the ground on 17 February 1883, losing 3–6 to local side Rawtenstall. When Prince Albert Victor opened a new hospital in Burnley in 1886, Turf Moor became the first football ground to be visited by a member of the British Royal Family. The first Football League match at the ground took place on 6 October 1888, with Fred Poland scoring the first competitive goal at the stadium. The record attendance at Turf Moor was set in 1924 when 54,755 people attended an FA Cup tie between Burnley and Huddersfield Town. In the same year, Turf Moor hosted its only FA Cup semi-final to date. In 1927, the stadium was the venue of an international match between England and Wales. Since then, the ground has been used to host matches in the European Under-19 Championship and European Under-21 Championship fixtures.\r\n\r\nIn 2007, plans for expansion of Turf Moor were released to the public. The Burnley directors proposed a major development of the stadium, costing £20 million. The plans, which would incorporate the rebuilding of the Cricket Field Stand and the moving of the players' entrance tunnel, have been put on hold until the current financial climate improves. In 2009, following Burnley's promotion to the Premier League, the development of a second tier on the Bob Lord stand was announced. Director Paul Fletcher stated that the anticipated capacity of Turf Moor would eventually be 28,000 to cope with extra fans. However following the club's relegation from the Premier League in 2010, these plans were put on hold indefinitely.\r\n\r\nAfter again achieving promotion to the Premier League in 2014 further Ground renovations were undertaken to comply with new regulations include housing for International Media, relocation of the players tunnel and upgrades to seating in the David Fishwick Stand for away supporters. On 26 June 2014 the club unveiled a planning application for extensions to the Turf Moor front entrance including the rebuilding of the official Club Shop containing a first floor museum.",
            "strStadiumLocation": "Harry Potts Way, Burnley, Lancashire",
            "intStadiumCapacity": "21401",
            "strWebsite": "www.burnleyfootballclub.com",
            "strFacebook": "www.facebook.com/officialburnleyfc",
            "strTwitter": "www.twitter.com/burnleyofficial",
            "strInstagram": "",
            "strDescriptionEN": "Burnley Football Club /ˈbɜrnli/ are a professional football club based in Burnley, Lancashire, who play in the Premier League, the highest level of English football. Nicknamed The Clarets, due to the dominant colour of their home shirts, they were one of the founder members of the Football League in 1888. The club colours of claret and blue were adopted in 1910 in tribute to the dominant club of English football at the time, Aston Villa. It was thought the colours might lift and inspire Burnley to emulate the aforementioned side. Their home ground since 1883 has been Turf Moor.\r\n\r\nBurnley have been Football League Champions twice, in 1920–21 and 1959–60, and have won the FA Cup once, in 1914. The Clarets also reached the 1961 quarter-finals of the European Cup. They are one of only three teams to have won all top four professional divisions of English football, along with Wolverhampton Wanderers and Preston North End.\r\n\r\nThe club spent most of their early history in England's top two divisions, but remained outside the top flight from 1976 to 2009. From 1985 to 1992 they had a seven-year spell in the lowest tier of the Football League. In 1987 they narrowly avoided relegation to the Conference. Between 2000 and 2009 they played in the second tier of English football, until they gained promotion to the Premier League for the first time in 33 years after winning the 2009 Championship play-off final, but were relegated after a single season. The club currently play in the Premier League again in 2014–15 after being promoted from the Football League Championship at the end of the 2013–14 season as runners-up to Leicester City.",
            "strDescriptionDE": null,
            "strDescriptionFR": null,
            "strDescriptionCN": null,
            "strDescriptionIT": null,
            "strDescriptionJP": null,
            "strDescriptionRU": null,
            "strDescriptionES": null,
            "strDescriptionPT": null,
            "strDescriptionSE": null,
            "strDescriptionNL": null,
            "strDescriptionHU": null,
            "strDescriptionNO": null,
            "strDescriptionIL": null,
            "strDescriptionPL": null,
            "strGender": "Male",
            "strCountry": "England",
            "strTeamBadge": "https://www.thesportsdb.com/images/media/team/badge/sqrttx1448811003.png",
            "strTeamJersey": "https://www.thesportsdb.com/images/media/team/jersey/3g25co1510755094.png",
            "strTeamLogo": "https://www.thesportsdb.com/images/media/team/logo/vstywy1469522470.png",
            "strTeamFanart1": "https://www.thesportsdb.com/images/media/team/fanart/qxtvww1424229257.jpg",
            "strTeamFanart2": "https://www.thesportsdb.com/images/media/team/fanart/vwuryy1469522328.jpg",
            "strTeamFanart3": "https://www.thesportsdb.com/images/media/team/fanart/truwwr1424229288.jpg",
            "strTeamFanart4": "https://www.thesportsdb.com/images/media/team/fanart/tuswuy1469522211.jpg",
            "strTeamBanner": "https://www.thesportsdb.com/images/media/team/banner/vtwups1424563418.jpg",
            "strYoutube": "www.youtube.com/user/officialburnleyfc",
            "strLocked": "unlocked"
            }
        ]
    }
    """.trimIndent()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = FavmatchPresenter(view, fetch, db)
    }

    @Test
    fun show_favmatch_when_getFavmatch_notempty() {
        `when`(db.getFavs()).thenReturn(MANY_FAVS)

        doAsync {
            `when`(Gson().fromJson(jsonEvent,
                    Events::class.java
            )).thenReturn(EVENTS)

            presenter.getFavmatch()

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
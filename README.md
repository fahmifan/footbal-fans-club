# Submission 4
---

## Unit testing
### FavMatchPresenterTest.kt
1. Test 01
- get favs match from DB and return many favs
- then get `Events` from network (mocked using json string)
- then it check if `showLoading` and `setFavmatch` are called

2. Test 02
- get favs from DB and return no favs
- then it check if `showLoading` and `showNoFavs` are called
  
### SchedulePresenter.kt
- It should show events when getEvents notempty
  - get `Events` from network (mocked using json string) and return non-empty `Events`
  - then it check if `showLoading` and `showEvents` are called

## DateTime.kt
- It assert if the date string passed in is return in formated style

## Instrumentation testing
### MainActivityTest.kt
1. check if there is a bottom navbar
2. then, click on the `past match` button
3. wait for 5sec
4. check, if there is text `Everton` then click on it. We should on detail view
5. check if there is a `Goal` text displayed there
6. go back to main view
7. click on `next match`
8. wait for 5sec
9. check if there is text `Tottenham`. Then clicked it, we should on detail view
10. click `Love` button, it will add match to favorite list
11. wait 5sec when add match to favorite
12. back to main view
13. click on `Favorite` button
14. wait 5sec to load favorite match(es)
15. check if the layout is there
16. finish

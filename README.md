# Movie App Documentation
Movie App - check current and upcoming movie lists in your nearby movie theaters.

# Basic Functionalities:

• 1 central page with two tabs at the top
	
    o 1 tab titled “Now Playing”
	o 1 tab titled “Upcoming movies”
• Each tab shows a list of movies according to the tab selected

	o Each row item (CardView) has:
		 Movie name
		 Movie popularity
• Movie Detail Page:

	o Click movie in the list (RecyclerView item click) moves to movie detail page.
	o Movie details page contains: Movie Title, Movie Image, Overview, Release Date, User Rating. 
# Extra Features:

 - Pagination mechanism: Implemented using lazy list
 - Show at least 1 associated image for each movie row item
 - Error retry handling (No Internet connection) If internet connection gets lost, App opens snackbar and user can hit retry to connect to internet again.
 - Configuration Changes: Used saved instance state bundle to store the object of movie list and retrieving the same object by the same bundle. This feature saves number of calls made to the backend.
 - Animation Used gridLayoutAnimation, that shows animation from left to right and top to bottom while scrolling.
 - Swipe Refresh Layout To refresh the whole page.
 
# Pattern:
1) Adapter Pattern
2) ViewHolder Pattern
 
 # Libraries Used:
1) Retrofit
2) Glide
3) Gson
4) RecyclerView with Cardview

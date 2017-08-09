# DudeWheresMyMovies

This is a very simple android app. It uses TMDB to help you find movies and see there ratings and decsription and even the trailers.
When you find a movie you like you can add it to a watch list in the app. 

# UI(Phone)




Main Screen                                                                          |  Detail Fragment
:-------------------------:                                                          |:-------------------------:
<img src="/Screenshot_20170808-010049.png" alt="alt text" width="320" height="620">  |  <img src="/Screenshot_20170808-010104.png" alt="alt text" width="320" height="620">

# UI(Tablet)

Here I am using Android's Master/Detail Flow to take advantage of the tablets bigger screen. Because each screen is a fragment instead of an activity
they can both be displayed on the same screen. A callback function links the data from the main fragment to the detail fragment. This design pattern is useful when working with large screens.
If the app was still two seperate views, like on the phone, but scaled to the screen size it just feels bulky and unatural. 

<img src="/Screenshot_20170808-022958.png" alt="alt text" width="720" height="420">


# Thoughts

This is one of the first android apps I worked on and it taught me a lot. The way android does threading is really what made me love this platform.
AsyncTask's have there limitation but it makes it so easy to get data from a background thread back to the main thread. Over all I got to know a lot about android in this one.
The UI my favorite part to play around with and I really understand the importance of an adaptive UI now. When I first built this app I was running it on an HTC One running kit-kat.
I recently ran it on my pixel XL(captured in the pictures above) and the UI looks even better than it did back then. I am really happy with the way I did the UI in this project.

I had never really worked with JSON before this project. All the api's I have used in the past went through the trouble of calling the server and assigning the values to pre defined objects for you.
So before I started working with android I never had a need to deal with JSON or try and parse it myself. I thought there would be a bit of a learning curve but to my suprise it was super easy to read and work with.
The data was really easy to organize into my own object that I used to hold all the data for the movies. While I was learning how much I love working with JSON I was also learning how much I don't like SQLite.
I used SQLite for a local database to store the users watch list of movies. Content providers are not to hard to write and that isn't really what I took issue with when making the SQLite database.
Honestly it was just how confusing and complex it gets when trying to link tables to data in other tables. Which is why I scraped the idea of storing anything other than the movie title, synopsis and rating in the database.
When the app is offline you can still view your watchlist but all you will see is the title synopsis and rating. Each movie has multiple reviews and trailers, so each trailer and review is stored in a list inside the Movie object.
I just could not find a "sane way" of storing those lists in the SQL database. 

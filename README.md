# Premise_Weather

This project showcases some features in a hypothetical Android Weather App. 

It was build with an MVVM Architecture using xml binding expressions, ViewModel, Observers for the ViewModel interactions.

For the networking it employs RxJava to handle api calls with Retrofit/OkHttp.

Navigation Components, the Android equivalent of iOS Storyboards, performs switching between fragments while using a sigle activity for the entire app.

This app can accept arbitrary place name or lat lon coordinates as text and it will lookup the nearest matching Weather Station ID. It then fetches a 5 Day forcast.

Integration Tests were written in Espresso, and there is room for some Roboelectric tests as well particularly for unit testing the networking components.

Dagger2 is used for the Dependency Injection. Shout out to my friend Christian Gruber for his work writing that framework. 

The visual design has not been addressed. It is a work in progress and Glide Image Library needs a custom adapter to load the svgs that the API provided so it is left for the future exercise.

A suite of Espresso tests are included to ensure basic functionality of the happy path use case. 

After Story-2 merge, a dependency conflict I have not had a chance to debug is preventing the test suite from running. 


https://www.metaweather.com/api/

The metaweather API powers this app. 

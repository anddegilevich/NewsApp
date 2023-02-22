# News Android App

## About project

<b>News App</b> is the simple news aggregator application on a popular stack.

## Technology Stack

<p>Android SDK, Fragment, Kotlin, MVVM, NavigationComponents, ViewModel, Room, LiveData, KotlinCoroutines, Retrofit, Hilt, Glide.</p>

## Features

<p>The application is written in Kotlin and is based on the Single Activity approach using Fragmnets and Navigation Components. The architecture of the application is MVVM.</p>
<p>The application downloads news using the News API (https://newsapi.org). Retrofit is used to generate and read requests to the REST API.</p>
<p>The application has the ability to download the latest most popular news, search for news by keyword, save news to the local Room database, and share the news.</p>
<p>Most of the actions in the application are performed in Coroutines. The fragment data is stored in its ViewModel in LiveData format.</p>
<p>Hilt is used as the DI. Glide is used to download images by URL.</p>

## Screenshots
<p float="left">
  <img src="screens/screen1.png" width="400" />
  <img src="screens/screen2.png" width="400" /> 
</p>

<p float="left">
  <img src="screens/screen3.png" width="400" />
  <img src="screens/screen4.png" width="400" /> 
</p>

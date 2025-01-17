
**PokeQuest** is a Pokemon's App 🍲 Android application 📱 built to demonstrate use of *Modern Android development* tools ❤️.

## About
It simply loads **Pokemon** data from API and stores it in persistence storage (i.e. Room Database). Posts will be always loaded from local database. Remote data (from API) and Local data is always synchronized. 
- This makes it offline capable 😃. 
- Clean and Simple UI.

API is used in this app. - https://api.pokemontcg.io *.


## Built With 🛠
- [Kotlin](https://kotlinlang.org/) - First class and official programming language for Android development.
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - For asynchronous and more..
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - Collection of libraries that help you design robust, testable, and maintainable apps.
  - [Jetpack Compose](https://developer.android.com/compose) - Jetpack Compose is Android’s recommended modern toolkit for building native UI
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes. 
  - [Room](https://developer.android.com/topic/libraries/architecture/room) - SQLite object mapping library.
- [Dependency Injection](https://developer.android.com/training/dependency-injection) - 
  - [Hilt-Dagger](https://dagger.dev/hilt/) - Standard library to incorporate Dagger dependency injection into an Android application.
  - [Hilt-ViewModel](https://developer.android.com/training/dependency-injection/hilt-jetpack) - DI for injecting `ViewModel`.
- [Retrofit](https://square.github.io/retrofit/) - A type-safe HTTP client for Android and Java.
- [Gson](https://github.com/google/gson/) - A modern JSON library for Kotlin and Java.
- [Coil](https://github.com/coil-kt) - An image loading library for Android.
- [Material Components for Android](https://github.com/material-components/material-components-android) - Modular and customizable Material Design UI components for Android.
- [Gradle Kotlin DSL](https://docs.gradle.org/current/userguide/kotlin_dsl.html) - For writing Gradle build scripts using Kotlin.

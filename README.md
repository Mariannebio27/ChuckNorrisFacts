# Chuck Norris Facts


## Resume 
With this application you can search for Chuck Norris facts. The application uses an API to perform the searches [chucknorris.io]( https://chucknorris.io/).

## Screens 
 ![Tela de erro](/pictures/welcome_screen.png)
 ![Tela de erro](/pictures/shimmer_fact_list_screen.png)
 ![Tela de erro](/pictures/fact_list_screen.png)
 ![Tela de erro](/pictures/shimmer_search_screen.png)
 ![Tela de erro](/pictures/search_screen.png)
 ![Tela de erro](/pictures/empty_error_screen.png)
 ![Tela de erro](/pictures/error_screen.png)

## Automation
Ktlint - the task validates whether the code standard complies with the lint. Use the `./gradlew ktlint` command to validate.

KtlintFormat - this task modifies the code so that it follows the lint pattern. Use the `./gradlew ktlintFormat` command to adjust the code.

## Continuous Integration
**GitHub CI**

CI tool that allows the creation of customized workflows for repositories on GitHub.

**Workflows**

In this application, two workflows were created. The first for `Master` branch and the second for `general` branches. The following are descriptions of the workflows.

Master - run unit tests, Ktlint and APK generation.

General - run unit tests and Ktlint.

## Architecture
I tried to follow the concepts of Clean Architecture, so I divided the project into:

* **app module**: module that is started when user opens the application. It contais the Apllication class which starts dependency injection;

* **presentation module**: where everything related to a View is (fragments, activities, adapters and View Models);

* **data module**: the data layer contains all the code necessary to retrieve the data, whether from a local database or a service;

* **domain module**: the business logic layer contains entities, use cases and interfaces to communicate with the data module;

* **core module**: it has some extensions and implementations that are used by the application (ex: SingleLiveData etc);

## Main dependencies
**Koin** - _dependence injection_
 <p> Library chosen for its simple implementation. As a negative point, there is some loss of performance when compared to other competitors, such as Dagger. There is no significant loss for this application. </p>

**Coroutines** - _dealing with threads and asynchronism_
 <p> Approach suggested by Google and working well with Live Data, makes good use of the device's Threads and Thread Pool, improving application performance. When compared to RxJava, which is its biggest competitor, its positive point is its smaller size and simplicity, its negative point is its error handling which is a little more manual. </p>

**Retrofit** - _HTTP requirements_
 <p> Retrofit is the most widespread library for handling HTTP requests, in addition to being easy to implement. </p>
 
 **Shimmer** - _animation_
 <p> The Facebook library allows you to introduce shimmering animations in a simple way. It is widely used to signal the loading of some content. </p>
 

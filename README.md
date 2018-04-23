# tdump

Tdump is a small app which displays data from https://api.tronalddump.io. To use, build in the staging flavour, to run Espresso tests, use the mock flavour.

The application is structured according to Model View Presenter approach, with a shared common datasource. 

Libraries used:  Compatibility and support libraries, ConstraintLayout, RecyclerView, CardView and FlowLayout are used for displaying the data. Retrofit and RxJava and Gson are used for network calls and parsing the data. Dagger 2 for dependency injection Mockito, JUnit and Espresso are used for testing.

# Auto_Sign

This repository contains the Java Server for the Sign Translation Project of the Programming 3 Module at Imperial College London.

The files are structured in the following way:

-src/main/Java/Auto_Sign contains the four main classes: 

1. AutoSignApplication (starts the app)
2. AutoSignController (contanis the main methods for controlling the behaviors of the app, such as mapping of the pages or sending http requets)
3. HomeView (Contains fields to hold the submitted url and methods to work on it)
4. WebSecurityConfig (Makes sure the inputted url is if type https and not http)

-src/main/Java/Auto_Sign/functions us a package containing classes with static methods. These were created in order to make the other main classes more ordered and easy to understand. The static methods contained in these classes are: 

1. CheckVideoExist (checks video is present in cloud storage) 
2. PrintDbUrl (creates string to display database in html)
3. URLSubmit (Inserts elements in database)
4. SendURL (post request to python server with url)
5. StartVideo (Creates new HomeView object and goes to result page to watch the video)
6. DownloadObject (Downloads translation video and streams it together with youtube one)

-src/main/Resources/static contains static elements used in the program such ass css files, images, buttons and fonts

-src/main/Resources/templates contains all the html pages of the web app

-src/test/Java/AutoSign contains three classes used for testing the program:
1. AutoSignApplicationTests
2. AutoSignControllerTests (Contains tests for the AutoSignController methods)
3. HomeViewTests (contains tests for the HomeView methods)


-Pom.xml file contains all the plugins and dependencies needed for the program to run

-Procfile and .travis.yml were used to deploy the project with Travis CI and Heroku

For any question or doubts contact us directly at fc619@ic.ac.uk

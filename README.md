# Waka Waka
In this project, I recreated pacman using the java processing library. All graphics and resources were provided by USYD.

## How to run
Note that as this poject was completed in 2020, it uses some older versions of java etc. 
To run this project, you need:
- Java 8
- Gradle 8.3
- The java processing library `import processing.core.PApplet; import processing.core.PFont;`
- junit
- jacoco

In order to play the game, run
```
$ gradle build
$ gradle test
```

- Note that gradle build will also run all of the tests and ensure they pass.

## Test report
The testing report can be viewed [here](/build/reports/tests/test/index.html)

The coverage report can be viewed [here](/build/reports/jacoco/test/html/index.html)

## Javadocs
The javadocs can be viewed [here](/build/docs/javadoc/index.html)

## Game design
The game design report can be viewed [here](/Waka%20Game%20Design%20Report.pdf)
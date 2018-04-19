# Tea 

This is a mobile friendly web page that shows you the current arbs between various cryptocurrency exchanges.

## Quick Start ##
```
gradle build go
google-chrome http://localhost:8081
```

## Directory Structure ##

### /cup ###
This is Kotlin JVM code.  It polls exchanges for prices and exposes a REST service that lets you retrieve them.

### /leaves ###
This is Kotlin JS code.  It runs in index.html, polls the REST server for data and displays it.

### /air ###
This is Kotlin JS code.
These are just declarations telling Kotlin that there are javascript libraries somewhere that will
implement this stuff in the browser.

### /water ###
This is Kotlin code that compiles to both JVM and JS.
This is where we put the transfer objects that get passed around with REST.
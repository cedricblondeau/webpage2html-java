# webpage2html-java
Java port of [zTrix/webpage2html](https://github.com/zTrix/webpage2html).
Encode external assets (js, css, images) with base64 and generate a single HTML file for a given URL.

## CLI usage using Gradle
```
./gradlew run -Dexec.args="https://www.google.com out.html"
```

## Dependencies
- [jsoup](https://github.com/jhy/jsoup)
- [OkHttp](https://github.com/square/okhttp)

## TODO
- Recursively transform CSS files (@import)
- Improve unit tests
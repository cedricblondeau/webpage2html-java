# webpage2html-java
Generate a single HTML file for a given URL by transforming external assets (css, js, images, fonts) into inline content and by encoding them with base64 if necessary.

Initially a Java port of [zTrix/webpage2html](https://github.com/zTrix/webpage2html). Still in early development.

### Known limitations
- HTTP user agent is default [OkHttp](https://github.com/square/okhttp) one ("okhttp/2.4.0"). Some websites may serve different content or just block requests. [User agent spoofing](https://en.wikipedia.org/wiki/User_agent#User_agent_spoofing) may fix this but I won't help with that.
- In a few cases, [data URIs (base64) could be slower than source linking](http://www.mobify.com/blog/data-uris-are-slow-on-mobile/)

### Dependencies
- [jsoup](https://github.com/jhy/jsoup)
- [OkHttp](https://github.com/square/okhttp)

### CLI usage using Gradle
```
./gradlew run -Dexec.args="http://rtw.cedricblondeau.com out.html"
```

### TODO
- Improve unit tests
- Recursively transform CSS files (@import)
- Transform JS files
- Improve performance
- Process "invalid" URLs
- Android integration
# webpage2html-java
Generates a single HTML file for a given URL by transforming external assets (css, js, images, fonts) into inline content and by encoding them with base64 if necessary.

Initially a Java port of [zTrix/webpage2html](https://github.com/zTrix/webpage2html).

Java 1.7 and Android compatible.

### Known limitations
- HTTP user agent is default [OkHttp](https://github.com/square/okhttp) one ("okhttp/2.4.0"). Some websites may serve different content or just block requests. [User agent spoofing](https://en.wikipedia.org/wiki/User_agent#User_agent_spoofing) may fix this but you should use this responsibly.
- [Data URIs (base64) could be slower than source linking](http://www.mobify.com/blog/data-uris-are-slow-on-mobile/)

### Dependencies
- [jsoup](https://github.com/jhy/jsoup)
- [OkHttp](https://github.com/square/okhttp)

### Usage
```java
// Build a WebPage2Html object from a java.net.URL object
URL url = new URL("http://rtw.cedricblondeau.com"); // Input URL, throws MalformedURLException
WebPage2Html webPage2Html = new WebPage2Html(url);

// Optionally: Pass a custom configuration object
Configuration configuration = new Configuration();
configuration.setUserAgent("Android"); // Custom user-agent
webPage2Html.setConfiguration(configuration);

// execute() method returns a WebPage2HtmlResult object
WebPage2HtmlResult webPage2HtmlResult = webPage2Html.execute(); // throws IOException
webPage2HtmlResult.getUrl();    // Actual URL, could be different from input URL (e.g. redirection)
webPage2HtmlResult.getTitle();  // HTML document title
webPage2HtmlResult.getHtml();   // Transformed HTML content
```

### CLI usage using Gradle
```
./gradlew run -Dexec.args="http://rtw.cedricblondeau.com out.html"
```

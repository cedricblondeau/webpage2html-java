package com.cedricblondeau.webpage2html.transformers;

import com.cedricblondeau.webpage2html.transformers.http.HttpCacheUtils;
import junit.framework.TestCase;
import org.jsoup.nodes.Element;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;

public class HtmlTransformerTest extends TestCase {

    private HtmlTransformer getHtmlTransformer(String html) {
        try {
            return new HtmlTransformer(html, new URL("http://www.cedricblondeau.com/"), "UTF-8");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void testShouldInjectMetaCharsetIfNotPresent() {
        String html = "<html><head><title>Héllo world!</title></head><body></body></html>";
        HtmlTransformer htmlTransformer = getHtmlTransformer(html);
        assertTrue(htmlTransformer.getDocument().head().getElementsByTag("meta").hasAttr("charset"));
    }

    public void testShouldTransformStyleAttributeValue() {
        String base64 = "WW91IGRpZG4ndCBzYXkgdGhlIG1hZ2ljIHdvcmQh";
        HttpCacheUtils.cacheMockResourceFromBase64("http://www.cedricblondeau.com/img/test.png", "image/png", base64);
        String html = "<div id='myDiv' style='background: url(/img/test.png) center center;' />";
        HtmlTransformer htmlTransformer = getHtmlTransformer(html);
        Element element = htmlTransformer.getDocument().getElementById("myDiv");
        assertTrue(element.attr("style").contains(String.format("data:image/png;base64,%s", base64)));
    }

    public void testShouldTransformLinkHref() {
        String css = "body { background: red; }";
        HttpCacheUtils.cacheMockResourceFromSource("http://www.cedricblondeau.com/css/test.css", "text/css", css);
        String html = "<link id='myLink' rel='stylesheet' href='/css/test.css' />";
        HtmlTransformer htmlTransformer = getHtmlTransformer(html);
        Element element = htmlTransformer.getDocument().getElementById("myLink");
        assertTrue(element.attr("href").contains(String.format("data:text/css;base64,%s", Base64.getEncoder().encodeToString(css.getBytes()))));
    }

}

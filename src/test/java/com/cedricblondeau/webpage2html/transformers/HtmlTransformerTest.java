package com.cedricblondeau.webpage2html.transformers;

import com.cedricblondeau.webpage2html.transformers.http.HttpCacheUtils;
import junit.framework.TestCase;
import org.jsoup.nodes.Element;

import java.net.MalformedURLException;
import java.net.URL;

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
        htmlTransformer.transform();
        assertTrue(htmlTransformer.getDocument().head().getElementsByTag("meta").hasAttr("charset"));
    }

    public void testShouldTransformStyleAttributeValue() {
        String base64 = "WW91IGRpZG4ndCBzYXkgdGhlIG1hZ2ljIHdvcmQh";
        HttpCacheUtils.cacheMockResourceFromBase64("http://www.cedricblondeau.com/img/test.png", "image/png", base64);
        String html = "<div id='myDiv' style='background: url(/img/test.png) center center;' />";
        HtmlTransformer htmlTransformer = getHtmlTransformer(html);
        htmlTransformer.transform();
        Element element = htmlTransformer.getDocument().getElementById("myDiv");
        assertTrue(element.attr("style").contains(String.format("data:image/png;base64,%s", base64)));
    }

    public void testShouldTransformLinkStylesheet() {
        String css = "body { background: red; }";
        HttpCacheUtils.cacheMockResourceFromSource("http://www.cedricblondeau.com/css/test.css", "text/css", css);
        String html = "<link rel='stylesheet' href='/css/test.css' />";
        HtmlTransformer htmlTransformer = getHtmlTransformer(html);
        htmlTransformer.transform();
        Element element = htmlTransformer.getDocument().getElementsByTag("style").first();
        assertEquals(css, element.html());
    }

    public void testShouldNotTransformLinkCanonical() {
        String html = "<link rel=\"canonical\" href=\"http:/www.cedricblondeau.com\">";
        HtmlTransformer htmlTransformer = getHtmlTransformer(html);
        htmlTransformer.transform();
        Element element = htmlTransformer.getDocument().getElementsByTag("link").first();
        assertEquals(html, element.outerHtml());
    }
}

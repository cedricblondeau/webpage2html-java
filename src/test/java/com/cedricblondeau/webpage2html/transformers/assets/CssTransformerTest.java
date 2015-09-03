package com.cedricblondeau.webpage2html.transformers.assets;

import com.cedricblondeau.webpage2html.Configuration;
import com.cedricblondeau.webpage2html.transformers.http.HttpCacheUtils;
import junit.framework.TestCase;

import java.net.MalformedURLException;
import java.net.URL;

public class CssTransformerTest extends TestCase {

    URL cssUrl;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        // CSS file URL
        try {
            cssUrl = new URL("http://www.cedricblondeau.com/css/test.css");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void testWithData() {
        String css = "background: url(data:image/png,base64:ABC!);";
        CssTransformer cssTransformer = new CssTransformer(css, cssUrl, new Configuration());
        assertEquals("Data should not be transformed", css, cssTransformer.getContent());
    }

    public void testWithPngImage() {
        String base64 = "WW91IGRpZG4ndCBzYXkgdGhlIG1hZ2ljIHdvcmQh";
        String mediaType = "image/png";
        HttpCacheUtils.cacheMockResourceFromBase64("http://www.cedricblondeau.com/img/test.png", mediaType, base64);
        String css = "background: url('/img/test.png');";
        CssTransformer cssTransformer = new CssTransformer(css, cssUrl, new Configuration());
        String expectedCss = String.format("background: url(\'data:%s;base64,%s\');", mediaType, base64);
        assertEquals("Image URL must be transformed to Base64", expectedCss, cssTransformer.getContent());
    }

}

package com.cedricblondeau.webpage2html.transformers.assets;

import com.cedricblondeau.webpage2html.http.HttpCache;
import com.cedricblondeau.webpage2html.http.resource.HttpDummyResource;
import com.cedricblondeau.webpage2html.http.resource.IHttpResource;
import junit.framework.TestCase;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;

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
        CssTransformer cssTransformer = new CssTransformer(css, cssUrl);
        assertEquals("Data should not be transformed", css, cssTransformer.getContent());
    }

    public void testWithPngImage() {
        try {
            // Cache some data
            String imageBase64 = "WW91IGRpZG4ndCBzYXkgdGhlIG1hZ2ljIHdvcmQh";
            IHttpResource pngResource = new HttpDummyResource(
                    new URL("http://www.cedricblondeau.com/img/test.png"),
                    "image/png",
                    null,
                    Base64.getDecoder().decode(imageBase64)
            );
            HttpCache.getInstance().put(pngResource.getUrl().toExternalForm(), pngResource);

            // Transform background property
            String css = "background: url('/img/test.png');";
            CssTransformer cssTransformer = new CssTransformer(css, cssUrl);
            String expectedCss = String.format("background: url(\'data:%s;base64,%s\');", pngResource.getMediaType(), imageBase64);
            assertEquals("Image URL must be transformed to Base64", expectedCss, cssTransformer.getContent());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

}

package com.cedricblondeau.webpage2html.transformers.http;

import com.cedricblondeau.webpage2html.http.HttpCache;
import com.cedricblondeau.webpage2html.http.resource.HttpDummyResource;
import com.cedricblondeau.webpage2html.http.resource.IHttpResource;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;

public class HttpCacheUtils {

    public static void cacheMockResourceFromBase64(String url, String mediaType, String base64) {
        try {
            IHttpResource resource = new HttpDummyResource(
                    new URL(url),
                    mediaType,
                    null,
                    Base64.getDecoder().decode(base64)
            );
            HttpCache.getInstance().put(resource.getUrl().toExternalForm(), resource);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static void cacheMockResourceFromSource(String url, String mediaType, String content) {
        try {
            IHttpResource resource = new HttpDummyResource(
                    new URL(url),
                    mediaType,
                    content,
                    content.getBytes()
            );
            HttpCache.getInstance().put(resource.getUrl().toExternalForm(), resource);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

}

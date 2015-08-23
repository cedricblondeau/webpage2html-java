package com.cedricblondeau.webpage2html.transformers.http;

import com.cedricblondeau.webpage2html.http.HttpCache;
import com.cedricblondeau.webpage2html.http.resource.HttpDummyResource;
import com.cedricblondeau.webpage2html.http.resource.HttpResource;

import java.net.MalformedURLException;
import java.net.URL;
import org.apache.commons.codec.binary.Base64;

public class HttpCacheUtils {

    public static void cacheMockResourceFromBase64(String url, String mediaType, String base64) {
        try {
            HttpResource resource = new HttpDummyResource(
                    new URL(url),
                    mediaType,
                    null,
                    Base64.decodeBase64(base64)
            );
            HttpCache.getInstance().put(resource.getUrl().toExternalForm(), resource);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static void cacheMockResourceFromSource(String url, String mediaType, String content) {
        try {
            HttpResource resource = new HttpDummyResource(
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

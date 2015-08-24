package com.cedricblondeau.webpage2html.http.resource;

import com.cedricblondeau.webpage2html.http.HttpCache;
import com.cedricblondeau.webpage2html.http.HttpRequest;
import com.squareup.okhttp.ResponseBody;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class HttpResourceFactory {

    private static final Logger logger = Logger.getLogger(HttpResourceFactory.class.getName());

    public HttpResource get(String resourceUrl, URL baseURL) {
        try {
            // Validate URL
            URL url = new URL(baseURL, resourceUrl);

            // Get HttpResource
            HttpResource httpResource;
            if (HttpCache.getInstance().has(url.toExternalForm())) {
                httpResource = (HttpResource) HttpCache.getInstance().get(url.toExternalForm());
            } else {
                HttpRequest httpRequest = new HttpRequest(url);
                ResponseBody responseBody = httpRequest.execute().body();
                httpResource = new HttpResponseResource(responseBody, url);
            }
            return httpResource;
        } catch (MalformedURLException e) {
            logger.log(Level.WARNING, e.getMessage());
            return null;
        } catch (NullPointerException e) {
            logger.log(Level.SEVERE, e.getMessage());
            return null;
        }
    }
}

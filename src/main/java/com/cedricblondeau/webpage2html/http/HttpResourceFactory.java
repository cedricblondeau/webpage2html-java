package com.cedricblondeau.webpage2html.http;

import com.cedricblondeau.webpage2html.Configuration;
import com.cedricblondeau.webpage2html.http.resource.HttpResource;
import com.cedricblondeau.webpage2html.http.resource.HttpResponseResource;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class HttpResourceFactory {

    private static final Logger logger = Logger.getLogger(HttpResourceFactory.class.getName());
    private Configuration configuration;

    public HttpResourceFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    public HttpResource get(String resourceUrl, URL baseURL) {
        // Validate URL
        URL url;
        try {
            url = new URL(baseURL, resourceUrl);
        } catch (MalformedURLException e) {
            logger.log(Level.WARNING, e.getMessage());
            return null;
        }

        // Get HttpResource
        try {
            HttpResource httpResource;
            if (HttpCache.getInstance().has(url.toExternalForm())) {
                httpResource = (HttpResource) HttpCache.getInstance().get(url.toExternalForm());
            } else {
                HttpRequest httpRequest = new HttpRequest(url, configuration);
                ResponseBody responseBody = httpRequest.execute().body();
                httpResource = new HttpResponseResource(responseBody, url);
            }
            return httpResource;
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
            return null;
        }
    }
}

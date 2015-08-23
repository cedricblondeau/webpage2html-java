package com.cedricblondeau.webpage2html.http.resource;

import com.cedricblondeau.webpage2html.http.HttpCache;
import com.cedricblondeau.webpage2html.http.HttpRequest;
import com.squareup.okhttp.ResponseBody;

import java.net.MalformedURLException;
import java.net.URL;

public final class HttpResourceFactory {

    public HttpResource get(String resourceUrl, URL baseURL) {
        try {
            // Validate URL
            URL url = new URL(baseURL, resourceUrl);
            System.out.println(String.format("Downloading %s", url));

            // Get HttpResource
            HttpResource httpResource;
            if (HttpCache.getInstance().has(url.toExternalForm())) {
                httpResource = (HttpResource) HttpCache.getInstance().get(url.toExternalForm());
            } else {
                HttpRequest httpRequest = new HttpRequest(url);
                ResponseBody responseBody = httpRequest.getResponse().body();
                httpResource = new HttpResponseResource(responseBody, url);
            }
            return httpResource;
        } catch (MalformedURLException e) {
            System.out.println(e.getMessage());
            return null;
        } catch (NullPointerException e) {
            return null;
        }
    }
}

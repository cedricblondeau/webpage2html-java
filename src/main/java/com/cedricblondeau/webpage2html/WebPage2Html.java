package com.cedricblondeau.webpage2html;

import com.cedricblondeau.webpage2html.http.HttpRequest;
import com.cedricblondeau.webpage2html.transformers.HtmlTransformer;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

public final class WebPage2Html {

    private URL url;
    private HtmlTransformer htmlTransformer;

    /**
     * Create a new WebPage2Html object with a given URL
     * @param url
     */
    public WebPage2Html(URL url) {
        this.url = url;
    }

    /**
     * - execute HTTP request with given URL,
     * - extract charset from HTTP response
     * - build HtmlTransformer object
     *
     * @return HtmlTransformer
     */
    public HtmlTransformer getHtmlTransformer() {
        try {
            // HTTP Request
            HttpRequest httpRequest = new HttpRequest(url);
            ResponseBody responseBody = httpRequest.getResponse().body();

            // Extract charset from HTTP response
            String content = responseBody.string();
            String charset = null;
            if (responseBody.contentType().charset() instanceof Charset) {
                charset = responseBody.contentType().charset().name();
            }

            // Build HtmlTransformer object
            htmlTransformer = new HtmlTransformer(content, url, charset);
            htmlTransformer.transform();
            return htmlTransformer;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

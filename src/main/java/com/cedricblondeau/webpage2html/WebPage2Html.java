package com.cedricblondeau.webpage2html;

import com.cedricblondeau.webpage2html.http.HttpRequest;
import com.cedricblondeau.webpage2html.transformers.HtmlTransformer;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

public final class WebPage2Html {

    private Configuration configuration;
    private URL requestURL;

    /**
     * Create a new WebPage2Html object & Execute HTTP request with given URL
     * @param url
     */
    public WebPage2Html(URL url) {
        requestURL = url;
    }

    /**
     * @param configuration
     */
    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    /**
     * - Execute HTTP request
     * - Extract content from HTTP response
     * - Build HtmlTransformer object
     * - Return a WebPage2HtmlResult object
     *
     * @return WebPage2HtmlResult
     */
    public WebPage2HtmlResult execute() throws IOException {
        // If no configuration given, create a default one
        if (configuration == null) {
            configuration = new Configuration();
        }

        // Execute request
        HttpRequest httpRequest = new HttpRequest(requestURL, configuration);
        Response httpResponse = httpRequest.execute();
        URL actualURl = httpResponse.request().httpUrl().url();

        // Extract content and charset
        ResponseBody responseBody = httpResponse.body();
        String content = responseBody.string();
        String charset = null;
        if (responseBody.contentType().charset() instanceof Charset) {
            charset = responseBody.contentType().charset().name();
        }

        // Build HtmlTransformer object and transform
        HtmlTransformer htmlTransformer = new HtmlTransformer(content, actualURl, charset, configuration);
        htmlTransformer.transform();

        // Build a WebPage2HtmlResult object
        WebPage2HtmlResult webPage2HtmlResult = new WebPage2HtmlResult(htmlTransformer);
        return webPage2HtmlResult;
    }
}

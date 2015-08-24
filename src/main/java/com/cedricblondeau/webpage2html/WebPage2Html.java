package com.cedricblondeau.webpage2html;

import com.cedricblondeau.webpage2html.http.HttpRequest;
import com.cedricblondeau.webpage2html.transformers.HtmlTransformer;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

public final class WebPage2Html {

    private URL actualURl;
    private Response httpResponse;
    private HtmlTransformer htmlTransformer;

    /**
     * Create a new WebPage2Html object & Execute HTTP request with given URL
     * @param url
     */
    public WebPage2Html(URL url) {
        HttpRequest httpRequest = new HttpRequest(url);
        httpResponse = httpRequest.execute();
        actualURl = httpResponse.request().httpUrl().url();
    }

    /**
     * Extract charset and content from HTTP response & build HtmlTransformer object
     * @return HtmlTransformer
     */
    public HtmlTransformer getHtmlTransformer() {
        try {
            // Extract response content
            ResponseBody responseBody = httpResponse.body();
            String content = responseBody.string();

            // Extract charset from HTTP response
            String charset = null;
            if (responseBody.contentType().charset() instanceof Charset) {
                charset = responseBody.contentType().charset().name();
            }

            // Build HtmlTransformer object
            htmlTransformer = new HtmlTransformer(content, actualURl, charset);
            htmlTransformer.transform();
            return htmlTransformer;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Returns the actual URL, could be different from given URL (e.g. redirection)
     * @return URL
     */
    public URL getUrl() {
        return actualURl;
    }
}

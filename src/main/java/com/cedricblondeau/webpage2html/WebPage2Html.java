package com.cedricblondeau.webpage2html;

import com.cedricblondeau.webpage2html.http.HttpRequest;
import com.cedricblondeau.webpage2html.transformers.HtmlTransformer;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

public final class WebPage2Html {

    private HtmlTransformer htmlTransformer;

    public WebPage2Html(URL url) {
        try {
            HttpRequest httpRequest = new HttpRequest(url);
            ResponseBody responseBody = httpRequest.getResponse().body();
            String content = responseBody.string();
            String charset = null;
            if (responseBody.contentType().charset() instanceof Charset) {
                charset = responseBody.contentType().charset().name();
            }
            htmlTransformer = new HtmlTransformer(content, url, charset);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getHtml() {
        return htmlTransformer.getHtml();
    }

    public String getTitle() { return htmlTransformer.getTitle(); }
}

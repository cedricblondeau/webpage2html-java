package com.cedricblondeau.webpage2html;

import com.cedricblondeau.webpage2html.http.HttpRequest;
import com.cedricblondeau.webpage2html.transformers.HtmlTransformer;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.net.URL;

public class WebPage2Html {

    HtmlTransformer htmlTransformer;

    public WebPage2Html(URL url) {
        try {
            HttpRequest httpRequest = new HttpRequest(url);
            ResponseBody responseBody = httpRequest.getResponse().body();
            htmlTransformer = new HtmlTransformer(
                    responseBody.string(),
                    url,
                    responseBody.contentType().charset().name()
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getHtml() {
        return htmlTransformer.getHtml();
    }
}

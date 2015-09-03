package com.cedricblondeau.webpage2html;

import com.cedricblondeau.webpage2html.transformers.HtmlTransformer;

import java.net.URL;

public final class WebPage2HtmlResult {

    private URL url;
    private String title;
    private String html;

    public WebPage2HtmlResult(HtmlTransformer htmlTransformer) {
        this.url = htmlTransformer.getUrl();
        this.title = htmlTransformer.getTitle();
        this.html = htmlTransformer.getHtml();
    }

    public URL getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public String getHtml() {
        return html;
    }
}

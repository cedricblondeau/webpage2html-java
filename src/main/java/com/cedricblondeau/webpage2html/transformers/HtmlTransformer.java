package com.cedricblondeau.webpage2html.transformers;

import com.cedricblondeau.webpage2html.transformers.assets.CssTransformer;
import com.cedricblondeau.webpage2html.transformers.assets.ITransformer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;

public class HtmlTransformer {

    protected Document document;
    protected URL baseUrl;
    protected String charset;

    public HtmlTransformer(String content, URL url, String charset) {
        document = Jsoup.parse(content, url.toExternalForm());
        this.baseUrl = url;
        this.charset = charset;
        transform();
    }

    protected void transform() {
        injectEncoding();
        transformStyle();
        transformLink();
        transformScript();
        transformImg();
    }

    protected void injectEncoding() {
        if (charset instanceof String) {
            boolean charsetDefinitionFound = document.head().getElementsByTag("meta").hasAttr("charset");
            if (!charsetDefinitionFound) {
                document.head().append(String.format("<meta charset=\"%s\"/>", charset));
            }
        }
    }

    protected void transformStyle() {
        Elements styleElements = document.getElementsByAttribute("style");
        for (Element element : styleElements) {
            CssTransformer cssTransformer = new CssTransformer(element.attr("style"), baseUrl);
            element.attr("style", cssTransformer.getContent());
        }
    }

    protected void transformLink() {
        Elements linkElements = document.getElementsByTag("link");
        for (Element element : linkElements) {
            String rel = element.attr("rel");
            if (!rel.isEmpty() && (rel.equals("stylesheet") || rel.equals("icon"))) {
                String href = element.attr("href");
                if (!href.isEmpty() && !href.startsWith("data:")) {
                    ITransformer transformer = new TransformerFactory().get(element.attr("href"), baseUrl);
                    if (transformer instanceof ITransformer) {
                        if (transformer instanceof CssTransformer) {
                            element.after(String.format("<style>%s</style>", ((CssTransformer) transformer).getContent()));
                            element.remove();
                        } else {
                            element.attr("href", transformer.getBase64());
                        }
                    }
                }
            }
        }
    }

    protected void transformScript() {
        Elements scriptElements = document.getElementsByTag("script");
        for (Element element : scriptElements) {
            if (element.hasAttr("src") && !element.attr("src").isEmpty() && !element.attr("src").startsWith("data:")) {
                ITransformer transformer = new TransformerFactory().get(element.attr("src"), baseUrl);
                if (transformer instanceof ITransformer) {
                    element.attr("src", transformer.getBase64());
                }
            }
        }
    }

    protected void transformImg() {
        Elements imgElements = document.getElementsByTag("img");
        for (Element element : imgElements) {
            if (element.hasAttr("src") && !element.attr("src").isEmpty() && !element.attr("src").startsWith("data:")) {
                ITransformer transformer = new TransformerFactory().get(element.attr("src"), baseUrl);
                if (transformer instanceof ITransformer) {
                    element.attr("src", transformer.getBase64());
                }
            }
        }
    }

    public Document getDocument() {
        return document;
    }

    public String getHtml() {
        return document.html();
    }
}

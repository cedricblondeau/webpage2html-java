package com.cedricblondeau.webpage2html.transformers;

import com.cedricblondeau.webpage2html.transformers.assets.CssTransformer;
import com.cedricblondeau.webpage2html.transformers.assets.Transformer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.logging.Logger;
import java.net.URL;

public final class HtmlTransformer {

    private Document document;
    private URL baseUrl;
    private String charset;
    private static final Logger logger = Logger.getLogger(HtmlTransformer.class.getName());

    public HtmlTransformer(String content, URL url, String charset) {
        document = Jsoup.parse(content, url.toExternalForm());
        this.baseUrl = url;
        this.charset = charset;
    }

    public void transform() {
        injectEncoding();
        transformStyle();
        transformLink();
        transformScript();
        transformImg();
    }

    private void injectEncoding() {
        if (charset instanceof String) {
            boolean charsetDefinitionFound = document.head().getElementsByTag("meta").hasAttr("charset");
            if (!charsetDefinitionFound) {
                logger.info(String.format("Injecting charset %s", charset));
                document.head().append(String.format("<meta charset=\"%s\"/>", charset));
            }
        }
    }

    private void transformStyle() {
        Elements styleElements = document.getElementsByAttribute("style");
        for (Element element : styleElements) {
            logger.info("Transforming inline style");
            CssTransformer cssTransformer = new CssTransformer(element.attr("style"), baseUrl);
            element.attr("style", cssTransformer.getContent());
        }
    }

    private void transformLink() {
        Elements linkElements = document.getElementsByTag("link");
        for (Element element : linkElements) {
            String rel = element.attr("rel");
            if (!rel.isEmpty() && (rel.equals("stylesheet") || rel.equals("icon"))) {
                String href = element.attr("href");
                if (!href.isEmpty() && !href.startsWith("data:")) {
                    logger.info(String.format("Transforming link %s", element.attr("href")));
                    Transformer transformer = new TransformerFactory().get(element.attr("href"), baseUrl);
                    if (transformer instanceof Transformer) {
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

    private void transformScript() {
        Elements scriptElements = document.getElementsByTag("script");
        for (Element element : scriptElements) {
            if (element.hasAttr("src") && !element.attr("src").isEmpty() && !element.attr("src").startsWith("data:")) {
                logger.info(String.format("Transforming script %s", element.attr("src")));
                Transformer transformer = new TransformerFactory().get(element.attr("src"), baseUrl);
                if (transformer instanceof Transformer) {
                    element.attr("src", transformer.getBase64());
                }
            }
        }
    }

    private void transformImg() {
        Elements imgElements = document.getElementsByTag("img");
        for (Element element : imgElements) {
            if (element.hasAttr("src") && !element.attr("src").isEmpty() && !element.attr("src").startsWith("data:")) {
                logger.info(String.format("Transforming image %s", element.attr("src")));
                Transformer transformer = new TransformerFactory().get(element.attr("src"), baseUrl);
                if (transformer instanceof Transformer) {
                    element.attr("src", transformer.getBase64());
                }
            }
        }
    }

    /**
     * @return JSoup Document
     */
    public Document getDocument() {
        return document;
    }

    /**
     * @return JSoup Document inner HTML
     */
    public String getHtml() {
        return document.html();
    }

    /**
     * @return JSoup Document title
     */
    public String getTitle() {
        return document.title();
    }
}

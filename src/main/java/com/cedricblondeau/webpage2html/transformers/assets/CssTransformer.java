package com.cedricblondeau.webpage2html.transformers.assets;

import com.cedricblondeau.webpage2html.Configuration;
import com.cedricblondeau.webpage2html.http.HttpResourceFactory;
import com.cedricblondeau.webpage2html.http.resource.HttpResource;

import java.net.URL;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TODO:
 * - Recursively transform CSS files (@import)
 */
public final class CssTransformer extends BaseTransformer implements Transformer {

    private String content;
    private URL baseURL;
    private Configuration configuration;
    private static final Logger logger = Logger.getLogger(CssTransformer.class.getName());

    /**
     * @param content CSS content
     * @param baseURL CSS file base URL
     */
    public CssTransformer(String content, URL baseURL, Configuration configuration) {
        super("text/css");
        this.content = content;
        this.baseURL = baseURL;
        this.configuration = configuration;
        transform();
    }

    /**
     * Replace each URL between url('*') with a base64 value
     */
    protected void transform() {
        Matcher m = Pattern.compile("url\\((.*?)\\)").matcher(content);
        while(m.find()) {
            String foundURL = m.group(1);
            foundURL = foundURL.replace("\"", "").replace("\'", "");
            if (!foundURL.startsWith("data:")) {
                logger.info(String.format("%s - Transforming %s", baseURL, foundURL));
                HttpResource httpResource = new HttpResourceFactory(configuration).get(foundURL, baseURL);
                if (httpResource instanceof HttpResource) {
                    BaseTransformer transformer = new BaseTransformer(httpResource.getMediaType());
                    transformer.setData(httpResource.getData());
                    content = content.replace(foundURL, transformer.getBase64());
                }
            }
        }
    }

    /**
     * @return String Transformed CSS content
     */
    public String getContent() {
        return content;
    }

    /**
     * @return String A String containing the resulting Base64 encoded characters
     */
    @Override
    public String getBase64() {
        setData(this.content.getBytes());
        return super.getBase64();
    }
}

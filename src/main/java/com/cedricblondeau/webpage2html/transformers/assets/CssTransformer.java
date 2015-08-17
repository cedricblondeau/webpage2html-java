package com.cedricblondeau.webpage2html.transformers.assets;

import com.cedricblondeau.webpage2html.http.resource.HttpResourceFactory;
import com.cedricblondeau.webpage2html.http.resource.IHttpResource;

import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TODO:
 * - Recursively transform CSS files (@import)
 */
public class CssTransformer extends BaseTransformer implements ITransformer {

    protected String content;
    protected URL baseURL;

    /**
     * @param content CSS content
     * @param baseURL CSS file base URL
     */
    public CssTransformer(String content, URL baseURL) {
        super("text/css");
        this.content = content;
        this.baseURL = baseURL;
        transform();
    }

    /**
     * Replace each URL between url('*') with a base64 value
     */
    protected void transform() {
        Matcher m = Pattern.compile("url\\((.*?)\\)").matcher(content);
        while(m.find()) {
            String foundURL = m.group(1);
            foundURL = foundURL.replace("\"", "");
            foundURL = foundURL.replace("\'", "");
            if (!foundURL.startsWith("data:")) {
                IHttpResource httpResource = new HttpResourceFactory().get(foundURL, baseURL);
                if (httpResource instanceof IHttpResource) {
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
        this.data = this.content.getBytes();
        return super.getBase64();
    }
}

package com.cedricblondeau.webpage2html.transformers;

import com.cedricblondeau.webpage2html.http.resource.HttpResourceFactory;
import com.cedricblondeau.webpage2html.http.resource.IHttpResource;
import com.cedricblondeau.webpage2html.transformers.assets.BaseTransformer;
import com.cedricblondeau.webpage2html.transformers.assets.CssTransformer;
import com.cedricblondeau.webpage2html.transformers.assets.ITransformer;

import java.net.URL;

public class TransformerFactory {

    /**
     * @param url
     * @param baseURL
     * @return ITransformer
     */
    public ITransformer get(String url, URL baseURL) {
        IHttpResource httpResource = new HttpResourceFactory().get(url, baseURL);
        return this.get(httpResource);
    }

    /**
     * @param httpResource
     * @return ITransformer
     */
    public ITransformer get(IHttpResource httpResource) {
        try {
            switch (httpResource.getMediaType()) {
                case "text/css":
                    return new CssTransformer(httpResource.getContent(), httpResource.getUrl());
                default:
                    BaseTransformer baseTransformer = new BaseTransformer(httpResource.getMediaType());
                    baseTransformer.setData(httpResource.getData());
                    return baseTransformer;
            }
        } catch (NullPointerException e) {
            return null;
        }
    }

}

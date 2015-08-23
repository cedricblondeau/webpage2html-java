package com.cedricblondeau.webpage2html.transformers;

import com.cedricblondeau.webpage2html.http.resource.HttpResourceFactory;
import com.cedricblondeau.webpage2html.http.resource.HttpResource;
import com.cedricblondeau.webpage2html.transformers.assets.BaseTransformer;
import com.cedricblondeau.webpage2html.transformers.assets.CssTransformer;
import com.cedricblondeau.webpage2html.transformers.assets.Transformer;

import java.net.URL;

public final class TransformerFactory {

    /**
     * @param url
     * @param baseURL
     * @return ITransformer
     */
    public Transformer get(String url, URL baseURL) {
        HttpResource httpResource = new HttpResourceFactory().get(url, baseURL);
        return this.get(httpResource);
    }

    /**
     * @param httpResource
     * @return Transformer
     */
    public Transformer get(HttpResource httpResource) {
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

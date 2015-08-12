package com.cedricblondeau.webpage2html.transformers.assets;

import java.util.Base64;

public class BaseTransformer implements ITransformer {

    protected byte[] data;
    protected Base64.Encoder encoder;
    protected String mediaType;

    /**
     * @param mediaType (e.g: text/css, text/javascript, etc.)
     */
    public BaseTransformer(String mediaType) {
        this.encoder = Base64.getEncoder();
        this.mediaType = mediaType;
    }

    /**
     * @param data
     */
    public void setData(byte[] data) {
        this.data = data;
    }

    /**
     * @return String A String containing the resulting Base64 encoded characters
     */
    @Override
    public String getBase64() {
        return String.format("data:%s;base64,%s", mediaType, encoder.encodeToString(data));
    }
}

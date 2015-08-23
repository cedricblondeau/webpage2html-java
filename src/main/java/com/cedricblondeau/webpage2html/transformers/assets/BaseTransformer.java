package com.cedricblondeau.webpage2html.transformers.assets;

import okio.ByteString;

public class BaseTransformer implements Transformer {

    private byte[] data;
    private String mediaType;

    /**
     * @param mediaType (e.g: text/css, text/javascript, etc.)
     */
    public BaseTransformer(String mediaType) {
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
        return String.format("data:%s;base64,%s", mediaType, ByteString.of(data).base64());
    }
}

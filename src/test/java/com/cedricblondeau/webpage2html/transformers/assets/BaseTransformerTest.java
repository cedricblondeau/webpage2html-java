package com.cedricblondeau.webpage2html.transformers.assets;

import junit.framework.TestCase;

import org.apache.commons.codec.binary.Base64;

public class BaseTransformerTest extends TestCase {

    public void testConvertToBase64() {
        String mediaType = "image/png";
        BaseTransformer baseTransformer = new BaseTransformer(mediaType);
        String imageBase64 = "R0lGODlhAQABAIAAAAAAAAAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==";
        byte[] imageData = Base64.decodeBase64(imageBase64);
        baseTransformer.setData(imageData);
        assertEquals("Resource must be transformed to Base64", String.format("data:%s;base64,%s", mediaType, imageBase64), baseTransformer.getBase64());
    }
}

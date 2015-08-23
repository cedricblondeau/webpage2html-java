package com.cedricblondeau.webpage2html.http.resource;

import java.net.URL;

public final class HttpDummyResource implements IHttpResource {
    private URL url;
    private String mediaType;
    private String content;
    private byte[] data;

    public HttpDummyResource(URL url, String mediaType, String content, byte[] data) {
        this.url = url;
        this.mediaType = mediaType;
        this.content = content;
        this.data = data;
    }

    @Override
    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    @Override
    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    @Override
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}

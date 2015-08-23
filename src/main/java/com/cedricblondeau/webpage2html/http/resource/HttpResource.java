package com.cedricblondeau.webpage2html.http.resource;

import java.net.URL;

public interface HttpResource {
    public URL getUrl();
    public byte[] getData();
    public String getContent();
    public String getMediaType();
}

package com.cedricblondeau.webpage2html;

public final class Configuration {
    private String userAgent = null;

    public String getUserAgent() {
        return userAgent;
    }

    public Configuration setUserAgent(String userAgent) {
        this.userAgent = userAgent;
        return this;
    }
}

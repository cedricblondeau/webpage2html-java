package com.cedricblondeau.webpage2html.http;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class HttpCache {

    private static final HttpCache instance = new HttpCache();
    private Map<String, Object> cache = Collections.synchronizedMap(new HashMap<String, Object>());

    private HttpCache() {}

    public static HttpCache getInstance() {
        return instance;
    }

    public void put(String cacheKey, Object value) {
        cache.put(cacheKey, value);
    }

    public boolean has(String cacheKey) {
        return cache.containsKey(cacheKey);
    }

    public Object get(String cacheKey) {
        return cache.get(cacheKey);
    }

    public void clear(String cacheKey) {
        cache.put(cacheKey, null);
    }

    public void clear() {
        cache.clear();
    }
}

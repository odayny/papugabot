package com.odayny.telegram.papugabot.model;

public class GoogleApiKeys {
    private String key;
    private String cx;

    public GoogleApiKeys(String key, String cx) {
        this.key = key;
        this.cx = cx;
    }

    public String getKey() {
        return key;
    }

    public String getCx() {
        return cx;
    }
}

package com.example.Short_url.service;

public interface UrlShorteningRedis {
    public void saveShortUrl(String shortKey, String longUrl);
    public String getLongUrl(String shortKey);
}

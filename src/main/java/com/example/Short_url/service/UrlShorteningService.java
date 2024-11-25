package com.example.Short_url.service;

import java.util.Map;

public interface UrlShorteningService {

    public String generateUrl(Map<String, Object> data);
    public String getLongKey(String shortKey);
    public String addService(Map<String, Object> data);
}

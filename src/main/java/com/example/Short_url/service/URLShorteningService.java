package com.example.Short_url.service;

import java.util.Map;

public interface URLShorteningService {

    public String generateURL(Map<String, Object> data);
    public String getLongKey(String shortKey);
    public String addService(Map<String, Object> data);
}

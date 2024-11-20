package com.example.Short_url.controller;

import com.example.Short_url.service.URLShorteningService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class ShortUrlController {

    private final URLShorteningService urlShorteningService;

    public ShortUrlController(URLShorteningService urlShorteningService) {
        this.urlShorteningService = urlShorteningService;
    }

    @PostMapping("/generate")
    public String generateShortUrl(@RequestBody Map<String, Object> data) {
        log.debug("REST request to generate ShortUrl: {}", data);
        urlShorteningService.generateURL(data);
        return "SUCCESS";
    }

}
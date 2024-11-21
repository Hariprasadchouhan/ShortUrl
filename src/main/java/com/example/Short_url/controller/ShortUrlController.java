package com.example.Short_url.controller;

import com.example.Short_url.dto.response.ShortUrlRedirectionDto;
import com.example.Short_url.dto.response.ShortUrlResponseDto;
import com.example.Short_url.service.URLShorteningService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class ShortUrlController {

    @Autowired
    private URLShorteningService urlShorteningService;


    @PostMapping("/generate")
    public ResponseEntity<ShortUrlResponseDto> generateShortUrl(@RequestBody Map<String, Object> data) {
        log.debug("REST request to generate ShortUrl: {}", data);
        ShortUrlResponseDto shortUrlResponseDto=new ShortUrlResponseDto(urlShorteningService.generateURL(data), LocalDate.now());
        return new ResponseEntity<>(shortUrlResponseDto,HttpStatus.OK);
    }

    @GetMapping(value = "{shortKey}")
    public ResponseEntity<ShortUrlRedirectionDto> redirect(@PathVariable String shortKey){
        ShortUrlRedirectionDto shortUrlRedirectionDto=new ShortUrlRedirectionDto(urlShorteningService.getLongKey(shortKey));
        return new ResponseEntity<>(shortUrlRedirectionDto,HttpStatus.OK);
    }

}
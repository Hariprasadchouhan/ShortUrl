package com.example.Short_url.controller;

import com.example.Short_url.service.UrlShorteningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/api/v1")
public class ServiceController {


    @Autowired
    private UrlShorteningService urlShorteningService;


    // Add Service
    @PostMapping("/service")
    public ResponseEntity<Object> addService(@RequestBody Map<String,Object> data){
        log.info("REST request to add Service: {}", data);
        String output = urlShorteningService.addService(data);
        return new ResponseEntity<>(output, HttpStatus.OK);
    }
}

package com.example.Short_url.service.impl;
import com.example.Short_url.model.ServiceMapping;
import com.example.Short_url.model.ShortUrl;
import com.example.Short_url.repository.ServiceRepository;
import com.example.Short_url.repository.ShortUrlRepository;
import com.example.Short_url.service.UrlShorteningRedis;
import com.example.Short_url.service.UrlShorteningService;
import com.example.Short_url.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

import static java.lang.String.format;

@Slf4j
@Service
public class UrlShorteningServiceImpl implements UrlShorteningService {
    @Autowired
    private ShortUrlRepository shortUrlRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private UrlShorteningRedis urlShorteningRedis;

    // Logic for saving ShortKey and LongKey
    @Override
    public String generateUrl(Map<String, Object> data) {
        log.debug("Service request to generate URL for the data {}", data);
        List<String> attributesValArray=new ArrayList<>();
        data.forEach((key, value) -> {attributesValArray.add(value.toString());});

        String serviceId=attributesValArray.get(0);
        String id=attributesValArray.get(1);
        String key= attributesValArray.get(2);
        int minLength=5;

        String longKey=generateLongKey(id,key);
        ServiceMapping serviceMapping=serviceRepository.findByserviceId(serviceId);
        String longUrl = format(serviceMapping.getUrlPattern(), longKey);
//      String decrypted=generateDecryptedKey(longKey);

        String shortKey=generateShortKey(longKey,minLength);
        urlShorteningRedis.saveShortUrl(shortKey, longUrl);
        ShortUrl shortUrl = new ShortUrl(shortKey,longUrl);
        shortUrlRepository.save(shortUrl);
        return format("https://licious.com/%s",shortKey);
    }

    //LongKey logic
    private String generateLongKey(String id,String key) {
        String originalKey = id.concat("#").concat(key);
        String encryptedKey = Utils.encryptDecrypt(originalKey,"encrypt");
        return Base64.getEncoder().encodeToString(encryptedKey.getBytes());
    }


    //Decrypt Logic
    private String generateDecryptedKey(String lonKey){
        byte[] decodedBytes = Base64.getDecoder().decode(lonKey);
        String decodedString = new String(decodedBytes);
        return Utils.encryptDecrypt(decodedString,"decrypt");
    }

    // ShortKey logic
    private String generateShortKey(String longKey,int minLength) {
        Hashids hashids = new Hashids("my-salt", minLength);
        int hash = Math.abs(longKey.hashCode());
        String hashidKey= hashids.encode(hash);
        return hashidKey.substring(0, minLength);
    }


    @Override
    public String getLongKey(String shortKey){
        String longUrl = urlShorteningRedis.getLongUrl(shortKey);
        if (longUrl != null) {
            log.info("Retrieved long URL from Redis: {}", longUrl);
            return longUrl;
        }
        ShortUrl document =shortUrlRepository.findByshortKey(shortKey);
        log.info("Retrived from database:{}",document);
        return document.getLongUrl();
        }

    @Override
    public String addService(Map<String, Object> data) {
        List<String> attributesValArray = new ArrayList<>();
        data.forEach((key, value) -> {
            attributesValArray.add(value.toString());
        });
        String serviceId = attributesValArray.get(0);
        String service = attributesValArray.get(1);
        String urlPattern=attributesValArray.get(2);
        ServiceMapping serviceMapping = new ServiceMapping(serviceId, service,urlPattern);
        serviceRepository.save(serviceMapping);
        return "Service added successfully";
    }

}
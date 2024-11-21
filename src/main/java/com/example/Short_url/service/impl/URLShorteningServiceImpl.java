package com.example.Short_url.service.impl;


import com.example.Short_url.model.ShortURL;
import com.example.Short_url.repository.ShortURLRepository;
import com.example.Short_url.service.URLShorteningService;
import com.example.Short_url.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class URLShorteningServiceImpl implements URLShorteningService {

    @Autowired
    private  ShortURLRepository shortURLRepository;



    // Logic for saving ShortKey and LongKey
    @Override
    public String generateURL(Map<String, Object> data) {
        log.debug("Service request to generate URL for the data {}", data);
        List<String> attributesValArray=new ArrayList<String>();
        data.forEach((key, value) -> {
            attributesValArray.add(value.toString());
        });
        log.info("attributesValArray: {}", attributesValArray);
        String id=attributesValArray.get(0);
        String key=attributesValArray.get(1);
        int minLength=5;
        String longKey=generateLongKey(id,key);
        log.info("longKey is :{}",longKey);

        String decrypted=generateDecryptedKey(longKey);
        log.info("decrypted :{}",decrypted);

        String shortKey=generateShortKey(longKey,minLength);
        log.info("shortKey is :{}",shortKey);

        ShortURL shortURL = new ShortURL(shortKey,longKey,data);
        log.info("shortURL is :{}",shortURL);

        shortURLRepository.save(shortURL);
        return shortKey;
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
       ShortURL document =shortURLRepository.findByshortKey(shortKey);
       log.info("Lonkey:{}",document);
        return document.getLongKey();
    }

}

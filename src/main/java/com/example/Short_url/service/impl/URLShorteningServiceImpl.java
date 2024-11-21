package com.example.Short_url.service.impl;

import com.example.Short_url.model.ShortURL;
import com.example.Short_url.repository.ShortURLRepository;
import com.example.Short_url.service.URLShorteningService;
import com.example.Short_url.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class URLShorteningServiceImpl implements URLShorteningService {

    @Autowired
    private  ShortURLRepository shortURLRepository;

//    public URLShorteningServiceImpl(ShortURLRepository shortURLRepository) {
//        this.shortURLRepository = shortURLRepository;
//    }

    @Override
    public String  generateURL(Map<String, Object> data) {
        log.debug("Service request to generate URL for the data {}", data);
        // TODO the logic for saving the data with shortKey & LongKey

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




        String shortKey=generateShortKey(longKey,minLength);
        log.info("shortKey is :{}",shortKey);

        ShortURL shortURL = new ShortURL(longKey,shortKey,data);
        log.info("shortURL is :{}",shortURL);

        shortURLRepository.save(shortURL);
        return shortKey;
    }


    private String generateLongKey(String id,String key) {

        //TODO logic of LongKey Generation
        String originalKey = id.concat("#").concat(key);
        String encryptedKey = Utils.encryptDecrypt(originalKey,"encrypt");
        return Base64.getEncoder().encodeToString(encryptedKey.getBytes());
    }



    private String generateShortKey(String longKey,int minLength) {
        // TODO logic of shortKey generation.
        Hashids hashids = new Hashids("my-salt", minLength);
        int hash = Math.abs(longKey.hashCode());

        String hashidKey= hashids.encode(hash);
        return hashidKey.substring(0, minLength);
    }
    @Override
    public String getLongKey(String shortKey) {
        ShortURL document=shortURLRepository.findByShortURL(shortKey);
        log.info("document is :{}",document);
        return document.getLongKey();
    }
}

package com.example.Short_url.service.impl;

import com.example.Short_url.model.ShortURL;
import com.example.Short_url.repository.ShortURLRepository;
import com.example.Short_url.service.URLShorteningService;
import com.example.Short_url.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.hashids.Hashids;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class URLShorteningServiceImpl implements URLShorteningService {

    private final ShortURLRepository shortURLRepository;

    public URLShorteningServiceImpl(ShortURLRepository shortURLRepository) {
        this.shortURLRepository = shortURLRepository;
    }

    @Override
    public void  generateURL(Map<String, Object> data) {
        log.debug("Service request to generate URL for the data {}", data);
        // TODO the logic for saving the data with shortKey & LongKey

        List<String> attributesValArray=new ArrayList<String>();
        data.forEach((key, value) -> {
            attributesValArray.add(value.toString());
        });
        String id=attributesValArray.get(0);
        String key=attributesValArray.get(1);

        int minLength=5;

        String longKey=generateLongKey(id,key);

        String shortKey=generateShortKey(longKey,minLength);

        ShortURL shortURL = new ShortURL(longKey,shortKey,data);

        shortURLRepository.save(shortURL);
    }
    private String generateLongKey(String id,String key) {

        //TODO logic of LongKey Generation
        String originalKey = id.concat("#").concat(key);
        String encryptedKey = Utils.encryptDecrypt(originalKey,"encrypt");
        String base64EncodedKey = Base64.getEncoder().encodeToString(encryptedKey.getBytes());
        return base64EncodedKey;
    }

    private String generateShortKey(String longKey,int minLength) {
        // TODO logic of shortKey generation.
        Hashids hashids = new Hashids("my-salt", minLength);
        int hash = Math.abs(longKey.hashCode());
        String hashidKey= hashids.encode(hash);
        return hashidKey.substring(0, minLength);
    }
    private String decryptedLongKey(String longKey,String decryptedKey) {
        String decodedBase64 = new String(Base64.getDecoder().decode(longKey));
        String originalCode=Utils.encryptDecrypt(decodedBase64,"decrypted");
        return originalCode;
    }
}

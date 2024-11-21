package com.example.Short_url.repository;

import com.example.Short_url.model.ShortURL;
import org.springframework.data.mongodb.repository.MongoRepository;



public interface ShortURLRepository extends MongoRepository<ShortURL, String> {
    ShortURL findByshortKey(String shortKey);
}

package com.example.Short_url.repository;

import com.example.Short_url.model.ShortURL;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface ShortURLRepository extends MongoRepository<ShortURL, String> {
    ShortURL findByshortKey(String shortKey);
}

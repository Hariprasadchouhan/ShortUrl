package com.example.Short_url.repository;

import com.example.Short_url.model.ShortUrl;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ShortUrlRepository extends MongoRepository<ShortUrl, String> {
    ShortUrl findByshortKey(String shortKey);
}

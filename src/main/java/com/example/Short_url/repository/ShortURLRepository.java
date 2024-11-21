package com.example.Short_url.repository;

import com.example.Short_url.model.ShortURL;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ShortURLRepository extends MongoRepository<ShortURL, String> {
    ShortURL findByShortURL(String shortURL);
}

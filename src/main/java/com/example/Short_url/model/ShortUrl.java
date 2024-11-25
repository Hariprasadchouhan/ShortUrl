package com.example.Short_url.model;


import lombok.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;


@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "ShortUrl")
public class ShortUrl {

    @Id
    private String shortKey;

    private String longUrl;

//    private String serviceId;

//    private Map<String,Object> attributeValues;


}

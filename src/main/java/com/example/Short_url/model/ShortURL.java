package com.example.Short_url.model;


import lombok.*;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;


@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "ShortUrl")
public class ShortURL {

    private String serviceId;

    private String shortKey;

    private String longKey;

    private Map<String,Object> attributeValues;


}

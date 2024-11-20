package com.example.Short_url.model;


import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Map;


@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "shorturl")
public class ShortURL {
//    private String _id;
//    private String serviceId;
    private String longKey;
    private String shortKey;

    private Map<String,Object> attributeValues;
//    private String createdBy;
//    private Date createdDate;
//    private String updatedBy;
//    private Date updatedDate;

}

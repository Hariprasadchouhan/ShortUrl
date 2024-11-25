package com.example.Short_url.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "ServiceMapping")
public class ServiceMapping {

    @Id
    private String serviceId;

    private String service;

    private String urlPattern;
}

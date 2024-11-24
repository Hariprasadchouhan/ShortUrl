package com.example.Short_url.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "ServiceMapping")
public class ServiceMapping {

    private String serviceId;

    private String service;
}

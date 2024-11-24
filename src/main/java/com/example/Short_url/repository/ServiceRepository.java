package com.example.Short_url.repository;

import com.example.Short_url.model.ServiceMapping;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends MongoRepository<ServiceMapping, String> {
      public ServiceMapping findByserviceId(String serviceId);
}

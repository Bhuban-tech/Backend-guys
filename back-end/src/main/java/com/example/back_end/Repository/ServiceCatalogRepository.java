package com.example.back_end.Repository;

import com.example.back_end.Entity.ServiceCatalogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServiceCatalogRepository extends JpaRepository<ServiceCatalogEntity, Long> {
    Optional<ServiceCatalogEntity> findByServiceName(String serviceName);
    Optional<ServiceCatalogEntity> findByServiceDescription(String serviceDescription);
}
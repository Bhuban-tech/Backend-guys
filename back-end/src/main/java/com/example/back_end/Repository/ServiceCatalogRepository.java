package com.example.back_end.Repository;

import com.example.back_end.Entity.ServiceCatalogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceCatalogRepository extends JpaRepository<ServiceCatalogEntity, Long> {
}

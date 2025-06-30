package com.example.back_end.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "service_catalog_entity")
public class ServiceCatalogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "service_name")
    private String serviceName;

    @Column(name = "service_description")
    private String serviceDescription;

    @Lob
    @Column(name = "image_data")
    private byte[] imageData;

    @Column(name = "image_type")
    private String imageType;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    public ServiceCatalogEntity(String serviceName, String serviceDescription, byte[] imageData, String imageType, User user) {
        this.serviceName = serviceName;
        this.serviceDescription = serviceDescription;
        this.imageData = imageData;
        this.imageType = imageType;
        this.user = user;
        this.creationDate = LocalDateTime.now();
    }
}
package com.example.back_end.Controller;

import com.example.back_end.Entity.ServiceCatalogEntity;
import com.example.back_end.Service.ServiceCatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/services")
public class ServiceCatalogController {

    @Autowired
    private ServiceCatalogService serviceService;

    @GetMapping
    public List<ServiceCatalogEntity> getAll() {
        return serviceService.getAllServices();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceCatalogEntity> getById(@PathVariable Long id) {
        return serviceService.getServiceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        return serviceService.getServiceById(id)
                .map(service -> {
                    MediaType mediaType;
                    try {
                        mediaType = MediaType.parseMediaType(service.getImageType());
                    } catch (Exception e) {
                        mediaType = MediaType.APPLICATION_OCTET_STREAM;
                    }
                    return ResponseEntity.ok()
                            .contentType(mediaType)
                            .body(service.getImageData());
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/create/{userId}")
    public String create(@PathVariable Long userId, @RequestBody ServiceCatalogEntity newService) {
        return serviceService.createService(userId, newService);
    }

    @PostMapping(value = "/upload/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadService(
            @PathVariable Long userId,
            @RequestPart("service_name") String name,
            @RequestPart("service_description") String description,
            @RequestPart("image") MultipartFile file) throws IOException {
        return serviceService.createServiceWithImage(userId, name, description, file);
    }

    @PutMapping("/{userId}/{id}")
    public String update(@PathVariable Long userId, @PathVariable Long id, @RequestBody ServiceCatalogEntity updatedService) {
        try {
            return serviceService.updateService(userId, id, updatedService)
                    .map(service -> "Service updated successfully")
                    .orElse("Service not found");
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    @DeleteMapping("/{userId}/{id}")
    public String delete(@PathVariable Long userId, @PathVariable Long id) {
        try {
            if (serviceService.deleteService(userId, id)) {
                return "Service deleted successfully";
            }
            return "Service not found";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }
}
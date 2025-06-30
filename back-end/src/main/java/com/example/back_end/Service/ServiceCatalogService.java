package com.example.back_end.Service;

import com.example.back_end.Entity.ServiceCatalogEntity;
import com.example.back_end.Entity.User;
import com.example.back_end.Repository.ServiceCatalogRepository;
import com.example.back_end.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceCatalogService {

    @Autowired
    private ServiceCatalogRepository serviceCatalogRepository;

    @Autowired
    private UserRepository userRepository;

    public List<ServiceCatalogEntity> getAllServices() {
        return serviceCatalogRepository.findAll();
    }

    public Optional<ServiceCatalogEntity> getServiceById(Long id) {
        return serviceCatalogRepository.findById(id);
    }

    public String createService(Long userId, ServiceCatalogEntity service) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null || !user.getRole().getRoleName().equalsIgnoreCase("ADMIN")) {
            return "Only admins can create services";
        }
        if (service == null) {
            return "Please provide service data";
        }
        if (service.getServiceName() == null || service.getServiceName().trim().isEmpty()) {
            return "Service name is required";
        }
        if (service.getServiceDescription() == null || service.getServiceDescription().trim().isEmpty()) {
            return "Service description is required";
        }
        if (serviceCatalogRepository.findByServiceName(service.getServiceName()).isPresent()) {
            return "This service name is already used";
        }
        if (serviceCatalogRepository.findByServiceDescription(service.getServiceDescription()).isPresent()) {
            return "A service with that description already exists";
        }
        service.setUser(user);
        serviceCatalogRepository.save(service);
        return "Service created successfully";
    }

    public String createServiceWithImage(Long userId, String name, String description, MultipartFile file) throws IOException {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null || !user.getRole().getRoleName().equalsIgnoreCase("ADMIN")) {
            return "Only admins can upload services";
        }
        if (name == null || name.trim().isEmpty()) {
            return "Service name is required";
        }
        if (description == null || description.trim().isEmpty()) {
            return "Service description is required";
        }
        if (file == null || file.isEmpty()) {
            return "Image file is required";
        }
        if (serviceCatalogRepository.findByServiceName(name).isPresent()) {
            return "This service name is already used";
        }
        if (serviceCatalogRepository.findByServiceDescription(description).isPresent()) {
            return "A service with that description already exists";
        }
        System.out.println("Image size: " + file.getBytes().length + " bytes");
        ServiceCatalogEntity service = new ServiceCatalogEntity();
        service.setServiceName(name);
        service.setServiceDescription(description);
        service.setImageData(file.getBytes());
        service.setImageType(file.getContentType());
        service.setUser(user);
        serviceCatalogRepository.save(service);
        return "Service uploaded successfully";
    }

    public Optional<ServiceCatalogEntity> updateService(Long userId, Long id, ServiceCatalogEntity updatedService) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null || !user.getRole().getRoleName().equalsIgnoreCase("ADMIN")) {
            throw new RuntimeException("Only admins can update services");
        }
        return serviceCatalogRepository.findById(id).map(existingService -> {
            if (updatedService.getServiceName() != null && !updatedService.getServiceName().trim().isEmpty()) {
                if (!updatedService.getServiceName().equals(existingService.getServiceName()) &&
                        serviceCatalogRepository.findByServiceName(updatedService.getServiceName()).isPresent()) {
                    throw new RuntimeException("This service name is already used");
                }
                existingService.setServiceName(updatedService.getServiceName());
            }
            if (updatedService.getServiceDescription() != null && !updatedService.getServiceDescription().trim().isEmpty()) {
                if (!updatedService.getServiceDescription().equals(existingService.getServiceDescription()) &&
                        serviceCatalogRepository.findByServiceDescription(updatedService.getServiceDescription()).isPresent()) {
                    throw new RuntimeException("A service with that description already exists");
                }
                existingService.setServiceDescription(updatedService.getServiceDescription());
            }
            if (updatedService.getImageData() != null) {
                existingService.setImageData(updatedService.getImageData());
                existingService.setImageType(updatedService.getImageType());
            }
            existingService.setUser(user);
            return serviceCatalogRepository.save(existingService);
        });
    }

    public boolean deleteService(Long userId, Long id) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null || !user.getRole().getRoleName().equalsIgnoreCase("ADMIN")) {
            throw new RuntimeException("Only admins can delete services");
        }
        return serviceCatalogRepository.findById(id).map(service -> {
            serviceCatalogRepository.delete(service);
            return true;
        }).orElse(false);
    }
}
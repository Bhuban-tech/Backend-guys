package com.example.back_end.Service;

import com.example.back_end.Entity.ServiceCatalogEntity;
import com.example.back_end.Repository.ServiceCatalogRepository;
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

    public List<ServiceCatalogEntity> getAllServices(){
        return serviceCatalogRepository.findAll();

    }

    public Optional<ServiceCatalogEntity> getServiceById(Long id){
        return serviceCatalogRepository.findById(id);
    }

    public ServiceCatalogEntity createService(ServiceCatalogEntity service) {
        return serviceCatalogRepository.save(service);
    }

    public Optional<ServiceCatalogEntity> updateService(Long id, ServiceCatalogEntity updated){
        return serviceCatalogRepository.findById(id).map(existing -> {
            existing.setServiceName(updated.getServiceName());
            existing.setServiceDescription(updated.getServiceDescription());
            return serviceCatalogRepository.save(existing);
        });
    }

    public boolean deleteService(Long id) {
        if (serviceCatalogRepository.existsById(id)) {
            serviceCatalogRepository.deleteById(id);
            return true;
        }
        return false;
    }
    public ServiceCatalogEntity createServiceWithImage(String name, String desc, MultipartFile file) throws IOException {
        ServiceCatalogEntity entity = new ServiceCatalogEntity();
        entity.setServiceName(name);
        entity.setServiceDescription(desc);
        entity.setImageData(file.getBytes());
        entity.setImageType(file.getContentType());

        return serviceCatalogRepository.save(entity);
    }

}

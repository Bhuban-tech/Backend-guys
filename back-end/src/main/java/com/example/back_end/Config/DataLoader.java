//package com.example.back_end.Config;
//
//
//import com.example.back_end.Entity.Role;
//import com.example.back_end.Entity.User;
//import com.example.back_end.Repository.RoleRepository;
//import com.example.back_end.Repository.UserRepository;
//import jakarta.annotation.PostConstruct;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class DataLoader {
//
//    @Autowired
//    private RoleRepository roleRepo;
//
//    @Autowired
//    private UserRepository userRepo;
//
//    @PostConstruct
//    public void loadInitialData() {
//
//        Role adminRole = roleRepo.findByRoleName("ADMIN");
//        if (adminRole == null) {
//            adminRole = new Role(1L, "ADMIN");
//            roleRepo.save(adminRole);
//        }
//
//        Role devRole = roleRepo.findByRoleName("DEVELOPER");
//        if (devRole == null) {
//            devRole = new Role(2L, "DEVELOPER");
//            roleRepo.save(devRole);
//        }
//
//        if (userRepo.findByUserName("hiyan").isEmpty()) {
//            User adminUser = new User();
//            adminUser.setUserName("hiyan");
//            adminUser.setEmail("raihiyanjng332@gmail.com");
//            adminUser.setRole(adminRole);
//            userRepo.save(adminUser);
//        }
//
//        if (userRepo.findByUserName("dev1").isEmpty()) {
//            User devUser = new User();
//            devUser.setUserName("ram");
//            devUser.setEmail("ram123@gmail.com");
//            devUser.setRole(devRole);
//            userRepo.save(devUser);
//        }
//
//        System.out.println("✅ Demo data loaded.");
//    }
//}

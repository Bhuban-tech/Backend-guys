package com.example.back_end.Repository;


import com.example.back_end.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleName(String roleName); // Optional, if needed
}

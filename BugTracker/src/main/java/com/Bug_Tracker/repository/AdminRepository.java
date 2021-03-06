package com.Bug_Tracker.repository;

import com.Bug_Tracker.Model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findAdminByUsername(String username);
}

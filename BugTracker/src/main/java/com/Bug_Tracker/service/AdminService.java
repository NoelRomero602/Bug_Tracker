package com.Bug_Tracker.service;

import com.Bug_Tracker.Model.Admin;
import com.Bug_Tracker.Model.User;
import com.Bug_Tracker.exception.domain.EmailExistException;
import com.Bug_Tracker.exception.domain.UsernameExistException;
import com.Bug_Tracker.exception.domain.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;



public interface AdminService {
    Admin register(String firstName, String lastName, String username, String password, String email) throws Exception, UsernameNotFoundException, UsernameExistException, EmailExistException;
    // Admin findAdminByUsername(String username);

List<User> getUsers();
    void deleteUser(Long id);
    User updateRole(String role, String username ) throws IOException;

}


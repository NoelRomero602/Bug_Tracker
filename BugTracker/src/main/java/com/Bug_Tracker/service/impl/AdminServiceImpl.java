package com.Bug_Tracker.service.impl;

import com.Bug_Tracker.Model.Admin;
import com.Bug_Tracker.Model.AdminPrinciple;
import com.Bug_Tracker.Model.User;
import com.Bug_Tracker.enumeration.Role;
import com.Bug_Tracker.exception.domain.EmailExistException;
import com.Bug_Tracker.exception.domain.UsernameExistException;
import com.Bug_Tracker.repository.AdminRepository;
import com.Bug_Tracker.repository.UserRepository;
import com.Bug_Tracker.service.AdminService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


@Service
@Qualifier("adminServiceImpl")
public class AdminServiceImpl implements AdminService, UserDetailsService {
    UserRepository userRepository; // synced up with db
    AdminRepository adminRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AdminServiceImpl(UserRepository userRepository, AdminRepository adminRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        // this.adminService = adminService;
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Admin register(String firstName, String lastName, String username, String password, String email) throws Exception, UsernameNotFoundException , UsernameExistException, EmailExistException {
        // validateNewUsernameAndEmail(EMPTY, username, email);
        Admin user = new Admin();
        user.setAdminId(generateUserId());
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(encodePassword(password));
        //   user.setActive(true);
        user.setRole(Role.ROLE_ADMIN.name());
        user.setAuthorities(Role.ROLE_ADMIN.getAuthorities());
        adminRepository.save(user); // saves user in mysql database
        return user;
    }
   /* private Admin validateNewUsernameAndEmail(String currentUsername, String newUsername, String newEmail) throws UsernameNotFoundException, UsernameExistException, EmailExistException {
        Admin userByNewUsername = findUserByUsername(newUsername);
        Admin userByNewEmail = findUserByEmail(newEmail);
        if(StringUtils.isNotBlank(currentUsername)) {
            User currentUser = findUserByUsername(currentUsername);
            if(currentUser == null) {
                throw new UsernameNotFoundException("No user found by username" + currentUsername);
            }
            if(userByNewUsername != null && !currentUser.getId().equals(userByNewUsername.getId())) {
                throw new UsernameExistException("Username already exists");
            }
            if(userByNewEmail != null && !currentUser.getId().equals(userByNewEmail.getId())) {
                throw new EmailExistException("Email already exists");
            }
            return currentUser;
        } else {

            if(userByNewUsername != null) {
                throw new UsernameExistException("Username already exists");
            }
            if(userByNewEmail != null) {
                throw new EmailExistException("Email already exists");
            }
            return null;
        }
    }*/
   /*
    @Override
    public User updateRole(String currentUsername, String newFirstName, String newLastName, String newUsername, String newEmail) throws IOException {
        User currentUser = findUserByUsername(currentUsername);//validateNewUsernameAndEmail(currentUsername, newUsername, newEmail);
        currentUser.setFirstName(newFirstName);
        currentUser.setLastName(newLastName);
        currentUser.setUsername(newUsername);
        currentUser.setEmail(newEmail);
        userRepository.save(currentUser);
        return currentUser;
    }
    */

    @Override
    public List<User> getUsers() {
        return null;
    }

    @Override
    public void deleteUser(Long id) {

    }

    @Override
    public User updateRole(String role, String username) throws IOException {
        return null;
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }


    public Admin findAdminByUsername(String username) {
        return this.adminRepository.findAdminByUsername(username);
    }

    /// these methods not needed

    private String generateUserId() {
        return RandomStringUtils.randomNumeric(10);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin user = adminRepository.findAdminByUsername(username);
        if(user == null){ throw new UsernameNotFoundException("User not found by username: " + username);}
        else
        {
            adminRepository.save(user);
            AdminPrinciple userPrincipal = new AdminPrinciple(user);
            return userPrincipal;
        }

    }


   /* @Override
    public User findUserByEmail(String email) {
        return null;
    }

    @Override
    public User findUserByUsername(String username) {
        return null;
    }

    @Override // let admin view all users in db
    public List<User> getUsers() {
        return userRepository.findAll();
    } // we make it of type list since findAll returns a list

    @Override // let admin remove a user from db
    public void deleteUser(Long id) {
        userRepository.deleteById(id); // this will delete from repo
    }

    public User updateRole(String role, String username) throws IOException {
        User user = userRepository.findUserByUsername(username);
        user.setRole(role);
        userRepository.save(user);
        return user; // returns object that sets role
    }*/
}


package com.Bug_Tracker.service.impl;
import com.Bug_Tracker.enumeration.Role;

import com.Bug_Tracker.Model.User;
import com.Bug_Tracker.Model.UserPrincipal;
import com.Bug_Tracker.exception.domain.EmailExistException;
import com.Bug_Tracker.exception.domain.UsernameExistException;
import com.Bug_Tracker.repository.UserRepository;
import com.Bug_Tracker.service.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.EMPTY;

@Transactional // data safety precaution especially for CRUD operations
@Service // same functionality as component except its service annotation
@Qualifier("UserDetailService") // this allows us to inject it directly to a method
public class UserServiceImpl implements UserService, UserDetailsService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

   // private Logger LOGGER = LoggerFactory.getLogger(getClass());
    @Autowired
    UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private String generateUserId() {
        return RandomStringUtils.randomNumeric(10);
    }
    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public User register(String firstName, String lastName, String username, String password, String email) throws UsernameNotFoundException ,UsernameExistException, EmailExistException {
       // validateNewUsernameAndEmail(EMPTY, username, email);
        User user = new User();
        user.setUserId(generateUserId());
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(encodePassword(password));
        user.setActive(true);
        user.setRole(Role.ROLE_USER.name());
        user.setAuthorities(Role.ROLE_USER.getAuthorities());
        userRepository.save(user); // saves user in mysql database
        return user;
    }

    private User validateNewUsernameAndEmail(String currentUsername, String newUsername, String newEmail) throws UsernameNotFoundException, UsernameExistException, EmailExistException {
        User userByNewUsername = findUserByUsername(newUsername);
        User userByNewEmail = findUserByEmail(newEmail);
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
    }





    private Role getRoleEnumName(String role) {
        return Role.valueOf(role.toUpperCase());
    }

   /* @Override
    public User updateUser(String currentUsername, String newFirstName, String newLastName, String newUsername, String newEmail) throws UsernameNotFoundException, UsernameExistException, EmailExistException {
        User currentUser = findUserByUsername(currentUsername);//validateNewUsernameAndEmail(currentUsername, newUsername, newEmail);
        currentUser.setFirstName(newFirstName);
        currentUser.setLastName(newLastName);
        currentUser.setUsername(newUsername);
        currentUser.setEmail(newEmail);
      //  currentUser.setActive(isActive);
      //  currentUser.setRole(getRoleEnumName(role).name());
      //  currentUser.setAuthorities(getRoleEnumName(role).getAuthorities());
        userRepository.save(currentUser);
        return currentUser;
    }*/


    @Override
    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(Long id) {
      userRepository.findById(id);

    }

    @Override
    public User updateRole(String role, String username) throws IOException {
        return null;
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);
        if(user == null){ throw new UsernameNotFoundException("User not found by username: " + username);}
        else
        {
            userRepository.save(user);
            UserPrincipal userPrincipal = new UserPrincipal(user);
            return userPrincipal;
        }

    }
}

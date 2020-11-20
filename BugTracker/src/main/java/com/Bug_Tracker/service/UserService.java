package com.Bug_Tracker.service;

import com.Bug_Tracker.domain.User;
import com.Bug_Tracker.exception.domain.EmailExistException;
import com.Bug_Tracker.exception.domain.UsernameExistException;
import com.Bug_Tracker.exception.domain.UsernameNotFoundException;

import java.io.IOException;
import java.util.List;

public interface UserService {
    User register(String firstName, String lastName, String username, String password, String email) throws UsernameNotFoundException,UsernameExistException, EmailExistException;

    List<User> getUsers();

    User findUserByUsername(String username);

    User findUserByEmail(String email);

    User addNewUser(String firstName, String lastName, String username, String email, String role, boolean isActive, String password) throws UsernameNotFoundException, UsernameExistException, EmailExistException, IOException;

    User updateUser(String currentUsername, String newFirstName, String newLastName, String newUsername, String newEmail, String role, boolean isActive) throws UsernameNotFoundException, UsernameExistException, EmailExistException, IOException;

    void deleteUser(Long id) throws IOException;


}
